function authFetch(url, options = {}) {
    const token = localStorage.getItem("jwtToken");
    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + token,
        "Content-Type": "application/json"
    };
    return fetch(url, options);
}