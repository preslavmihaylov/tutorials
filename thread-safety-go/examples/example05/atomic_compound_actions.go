// Package example05 shows two collections which both use thread-safe collections and yet, one of them is not thread-safe
// as it uses compound actions. Whenever there are compound actions, they should be synchronized together
package example05

import (
	"fmt"
	"sync"
)

func NewCompoundActionsUnsafe() *CompoundActionsUnsafe {
	return &CompoundActionsUnsafe{}
}

type CompoundActionsUnsafe struct {
	phonebook *sync.Map
}

func (p *CompoundActionsUnsafe) GetPhoneNumber(name string) (string, bool) {
	phone, ok := p.phonebook.Load(name)
	return fmt.Sprintf("%v", phone), ok
}

func (p *CompoundActionsUnsafe) SetPhoneNumber(name, phone string) {
	if _, ok := p.phonebook.Load(name); !ok {
		p.phonebook.Store(name, phone)
	}
}

func NewCompoundActionsSafe() *CompoundActionsSafe {
	return &CompoundActionsSafe{}
}

type CompoundActionsSafe struct {
	phonebook *sync.Map
}

func (p *CompoundActionsSafe) GetPhoneNumber(name string) (string, bool) {
	phone, ok := p.phonebook.Load(name)
	return fmt.Sprintf("%v", phone), ok
}

func (p *CompoundActionsSafe) SetPhoneNumber(name, phone string) {
	p.phonebook.LoadOrStore(name, phone)
}
