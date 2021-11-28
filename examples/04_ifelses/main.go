package main

import "fmt"

func main() {
	age := 14
	if age < 18 {
		fmt.Println("I'm too young to go to the pub 😭")
	} else if age > 18 && age <= 30 {
		fmt.Println("Let's parteeh! 🚀")
	} else {
		fmt.Println("I just want to stay home & watch netflix with my pet 🐈")
	}

	pet := "dog"
	switch pet {
	case "dog":
		fmt.Println("bark!")
	case "cat":
		fmt.Println("meoww")
	default:
		fmt.Println("dunno what that is...")
	}
}
