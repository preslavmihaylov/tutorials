package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

func main() {
	todosChan := make(chan *todo)
	for i := 1; i <= 10; i++ {
		go getTodo(todosChan, i)
	}

	for i := 1; i <= 10; i++ {
		todo := <-todosChan
		if todo.Title == "illo expedita consequatur quia in" {
			fmt.Println("found the todo I was looking for!")
		}
	}

	close(todosChan)
}

func getTodo(todosChan chan *todo, id int) {
	resp, err := http.Get(fmt.Sprintf("https://jsonplaceholder.typicode.com/todos/%d", id))
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()

	bs, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err)
	}

	var todo *todo
	err = json.Unmarshal(bs, &todo)
	if err != nil {
		panic(err)
	}

	fmt.Printf("sending TODO %d with title \"%s\"\n", todo.ID, todo.Title)
	todosChan <- todo
	fmt.Println("finished")
}

type todo struct {
	UserID    int    `json:"userId"`
	ID        int    `json:"id"`
	Title     string `json:"title"`
	Completed bool   `json:"completed"`
}
