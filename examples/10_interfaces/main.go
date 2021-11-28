package main

import "fmt"

func main() {
	printTheThing(&Person{"goshko", 18})
	printTheThing(&Dog{"richi"})
}

func printTheThing(p Printable) {
	p.Print()
}

type Printable interface {
	Print()
}

type Person struct {
	name string
	age  int
}

func (p *Person) Print() {
	fmt.Printf("I'm a person called %s and I'm %d years old.\n", p.name, p.age)
}

type Dog struct {
	name string
}

func (d *Dog) Print() {
	fmt.Printf("%s is here. Bark, bark!\n", d.name)
}
