package main

import "fmt"

func main() {
	a := 0
	incPtr(&a)
	inc(a)

	fmt.Println(a)
}

func incPtr(a *int) {
	(*a)++
}

func inc(a int) {
	a++
}
