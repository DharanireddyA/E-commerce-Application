function login() {
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();

  if (!email || !password) {
    alert("Please enter both email and password.");
    return;
  }

  fetch("http://localhost:8080/api/users/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  })
  .then(res => res.text())
  .then(response => {
    if (response === "Login successful!") {
      alert("Login successful!");
      localStorage.setItem("userEmail", email);
      window.location.href = "index.html"; // ✅ redirect after login
    } else {
      alert(response); // shows error from backend
    }
  })
  .catch(err => {
    alert("Login error");
    console.error(err);
  });
}