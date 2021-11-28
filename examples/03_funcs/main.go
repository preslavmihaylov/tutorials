package main

import "fmt"

func main() {
	justPrint(sum(5, 2))
	sum, sub := sumAndSub(5, 2)
	justPrint(sum, sub)
}

func sum(a, b int) int {
	return a + b
}

func sumAndSub(a, b int) (int, int) {
	return a + b, a - b
}

func justPrint(a ...int) {
	fmt.Println(a)
}
