package main

import "fmt"

func main() {
	p := &Person{"goshko", 18}

	p.printMe()
	p.levelUp()
	p.printMe()
}

type Person struct {
	name string
	age  int
}

func (p *Person) levelUp() {
	p.age++
}

func (p *Person) printMe() {
	fmt.Printf("my name is %s and I'm %d years old\n", p.name, p.age)
}
