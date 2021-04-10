// Package example06 contains two examples of non-thread-safe classes as they synchronize the writes but don't do so for the reads
// When synchronizing functions, you should always synchronize both reads and writes
package example06

import (
	"fmt"
	"sync"
)

type PartiallySafeHitCounter struct {
	lock    *sync.Mutex
	counter int
}

func (hitCounter *PartiallySafeHitCounter) Hit() {
	hitCounter.lock.Lock()
	defer hitCounter.lock.Unlock()

	hitCounter.counter++
}

func (hitCounter *PartiallySafeHitCounter) Get() int {
	return hitCounter.counter
}

var ready bool
var number int

func YouCantSeeMeRun() {
	for !ready {
	}

	if number != 42 {
		fmt.Printf("Wrong number! Expected: %v, Actual: %v.\n", 42, number)
	} else {
		fmt.Println("Run complete successfully...")
	}
}

func YouCantSeeMeInit() {
	number = 42
	ready = true
}
