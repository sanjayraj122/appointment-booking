function authFetch(url, options = {}) {
    options.credentials = "include"; // Send cookies with request
    options.headers = {
        ...options.headers,
        "Content-Type": "application/json"
    };
    return fetch(url, options);
}

// Example usage:
authFetch("/api/appointments")
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));