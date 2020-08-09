package main

import (
	"fmt"
	"net/http"
	"os"

	"go.uber.org/zap"
)

const logPath = "./logs/go.log"

var logger *zap.Logger

func init() {
	os.OpenFile(logPath, os.O_RDONLY|os.O_CREATE, 0666)
	c := zap.NewProductionConfig()
	c.OutputPaths = []string{"stdout", logPath}

	var err error
	logger, err = c.Build()
	if err != nil {
		panic(err)
	}
}

// statusWriter records the status written to ResponseWritter for later post processing
type statusWriter struct {
	http.ResponseWriter
	status int
	length int
}

func (w *statusWriter) WriteHeader(status int) {
	w.status = status
	w.ResponseWriter.WriteHeader(status)
}

func (w *statusWriter) Write(b []byte) (int, error) {
	if w.status == 0 {
		w.status = 200
	}

	n, err := w.ResponseWriter.Write(b)
	w.length += n
	return n, err
}

func main() {
	http.Handle("/payments/execute", loggingMiddleware(http.HandlerFunc(executePayment)))
	http.Handle("/payments/list", loggingMiddleware(http.HandlerFunc(listPayments)))
	http.Handle("/payments/authhold", loggingMiddleware(http.HandlerFunc(authHold)))

	fmt.Println("Listening on 8080...")
	http.ListenAndServe(":8080", nil)
}

func loggingMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		swriter := &statusWriter{w, 0, 0}
		next.ServeHTTP(swriter, r)

		fields := []zap.Field{
			zap.String("endpoint", r.URL.Path),
			zap.String("method", r.Method),
			zap.String("countryISO2", r.Header.Get("countryISO2")),
			zap.String("userID", r.Header.Get("userID")),
			zap.String("paymentMethod", r.Header.Get("paymentMethod")),
			zap.String("userType", r.Header.Get("userType")),
		}
		if swriter.status >= 400 {
			fields = append(fields,
				zap.Error(fmt.Errorf("Inbound request failed with status %d", swriter.status)))
			logger.Error("", fields...)
		} else {
			logger.Info("Inbound request succeeded", fields...)
		}
	})
}

func executePayment(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusBadRequest)
	w.Write([]byte("Payment successfully executed!"))
}

func listPayments(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Payments successfully listed!"))
}

func authHold(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Authorization Hold successful!"))
}
