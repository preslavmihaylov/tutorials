// Package example04 demonstrates a thread-safe struct which uses explicit synchronization via mutexes
// and a thread-safe struct which doesn't use any locks, and yet, is thread-safe as it uses a thread-safe collection
package example04

import (
	"fmt"
	"sync"
)

func NewSynchronizedPhonebook() *SynchronizedPhonebook {
	return &SynchronizedPhonebook{
		phonebook: map[string]string{},
	}
}

type SynchronizedPhonebook struct {
	lock      *sync.Mutex
	phonebook map[string]string
}

func (p *SynchronizedPhonebook) GetPhoneNumber(name string) (string, bool) {
	p.lock.Lock()
	defer p.lock.Unlock()

	phone, ok := p.phonebook[name]
	return phone, ok
}

func (p *SynchronizedPhonebook) SetPhoneNumber(name, phone string) {
	p.lock.Lock()
	defer p.lock.Unlock()

	p.phonebook[name] = phone
}

func NewConcurrentPhonebook() *ConcurrentPhonebook {
	return &ConcurrentPhonebook{}
}

type ConcurrentPhonebook struct {
	phonebook *sync.Map
}

func (p *ConcurrentPhonebook) GetPhoneNumber(name string) (string, bool) {
	phone, ok := p.phonebook.Load(name)
	return fmt.Sprintf("%v", phone), ok
}

func (p *ConcurrentPhonebook) SetPhoneNumber(name, phone string) {
	p.phonebook.Store(name, phone)
}
