function authFetch(url, options = {}) {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        window.location.href = "/signin";
        return Promise.reject("User not authenticated");
    }

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };

    return fetch(url, options);
}

// Example usage:
authFetch("/api/appointments")
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));