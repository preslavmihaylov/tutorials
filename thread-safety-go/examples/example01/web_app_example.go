// Package example01 contains an example of code which looks very humble and simple,
// and yet, is not thread-safe and can get you in trouble
package example01

import (
	"fmt"
	"net/http"
)

var usersToMessages = map[string][]string{}

// GetMessages route registered at /get
func GetMessages(w http.ResponseWriter, r *http.Request) {
	user := r.Header.Get("user")
	w.Write([]byte(fmt.Sprintf("%v", usersToMessages[user])))
	w.WriteHeader(http.StatusOK)
}

// SendMessage route registered at /send
func SendMessage(w http.ResponseWriter, r *http.Request) {
	user := r.Header.Get("user")
	msg := r.Header.Get("message")
	usersToMessages[user] = append(usersToMessages[user], msg)

	w.WriteHeader(http.StatusOK)
}
