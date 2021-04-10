// Package example02 contains two stateless functions,
// which don't need any synchronization although one of them has a lock which is unnecessary
package example02

import "sync"

var lock *sync.Mutex

func weekendsSafe() map[int]string {
	lock.Lock()
	defer lock.Unlock()

	weekends := map[int]string{}
	weekends[6] = "Saturday"
	weekends[7] = "Sunday"

	return weekends
}

func weekendsUnsafe() map[int]string {
	weekends := map[int]string{}
	weekends[6] = "Saturday"
	weekends[7] = "Sunday"

	return weekends
}
