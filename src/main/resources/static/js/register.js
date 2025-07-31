function register() {
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();

  fetch("http://localhost:8080/api/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ name, email, password })
  })
    .then(res => {
      if (!res.ok) {
        throw new Error("Signup failed");
      }
      return res.json();
    })
    .then(data => {
      alert("Registered successfully!");
      window.location.href = "login.html";
    })
    .catch(err => {
      alert("Error during signup");
      console.error(err);
    });
}