// Package example03 contains an example of a counter which is not thread-safe and a thread-safe version
package example03

import "sync"

type UnsafeHitCounter struct {
	counter int
}

func (hitCounter *UnsafeHitCounter) Hit() {
	hitCounter.counter++
}

func (hitCounter *UnsafeHitCounter) Get() int {
	return hitCounter.counter
}

type SafeHitCounter struct {
	lock    *sync.Mutex
	counter int
}

func (hc *SafeHitCounter) Hit() {
	hc.lock.Lock()
	defer hc.lock.Unlock()

	hc.counter++
}

func (hc *SafeHitCounter) Get() int {
	hc.lock.Lock()
	defer hc.lock.Unlock()

	return hc.counter
}
