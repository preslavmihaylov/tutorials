package main

import (
	"fmt"
	"time"
)

var (
	aInt   int   = 400
	aInt8  int8  = 100
	aUInt8 uint8 = 100
	// etc...

	aBool        bool      = true
	aFloat32     float32   = 3.1444444444444444444 // not gonna make it...
	aFloat64     float64   = 3.1444444444444444444
	aRune        rune      = '🚀'
	aString      string    = "hello world"
	aComplexType time.Time = time.Now().Add(5 * time.Minute)
)

func main() {
	fmt.Println("ints:", aInt, aInt8, aUInt8)
	fmt.Println("bool:", aBool)
	fmt.Println("floats:", aFloat32, aFloat64)
	fmt.Println("rune, string:", aRune, aString)
	fmt.Println("complex type (time):", aComplexType)
}
