package main

import (
	"errors"
	"fmt"
	"log"
)

func main() {
	nextLevel, err := levelUp(-5)
	if err != nil {
		log.Fatalf("couldn't level up: %s", err)
	}

	fmt.Printf("my level is %d\n", nextLevel)
}

func levelUp(level int) (int, error) {
	if level <= 0 {
		return 0, errors.New("your level can't be negative")
	}

	return level + 1, nil
}
