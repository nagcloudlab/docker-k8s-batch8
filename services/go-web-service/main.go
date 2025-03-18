package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

// HomeHandler serves the homepage
func HomeHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello, World! This is a Go web application")
}

// HealthCheckHandler serves the health check endpoint
func HealthCheckHandler(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	fmt.Fprintln(w, "OK")
}

func main() {
	r := mux.NewRouter()
	r.HandleFunc("/", HomeHandler)
	r.HandleFunc("/health", HealthCheckHandler)

	port := "8080"
	log.Println("Starting server on port", port)
	err := http.ListenAndServe(":"+port, r)
	if err != nil {
		log.Fatal("Server failed:", err)
	}
}
