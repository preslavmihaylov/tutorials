package main

import "fmt"

func main() {
	for i := 0; i < 10; i++ {
		fmt.Println("counting...", i)
	}

	// oh, you though Go had while loops, didn't you?
	i := 0
	for i < 10 {
		fmt.Println("counting...", i)
		i++
	}

	for _, pet := range []string{"cat", "dog", "rabbit"} {
		fmt.Println("I love my", pet)
	}
}
