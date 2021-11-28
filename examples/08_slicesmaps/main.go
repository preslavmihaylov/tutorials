package main

import "fmt"

func main() {
	vals := []string{"it's", "a", "beautiful"}
	fmt.Println(vals)

	vals = append(vals, "world")
	fmt.Println(vals)

	vals = vals[1:]
	fmt.Println(vals)

	vals = append(vals[:1], vals[2:]...)
	fmt.Println(vals)

	addresses := map[string]string{
		"goshko": "zona b-18",
		"toshko": "manastirski livadi",
	}

	addresses["moshko"] = "mladost"
	for person, address := range addresses {
		fmt.Printf("%s jivee v %s\n", person, address)
	}
}
