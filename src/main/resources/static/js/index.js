function addToCart(button) {
  const productId = button.dataset.id;
  const productName = button.dataset.name;
  const image = button.dataset.image;
  const price = button.dataset.price;

  const payload = {
    userId: 1,
    productId: productId,
    productName: productName,
    image: image,
    price: price,
    quantity: 1
  };

  fetch("http://localhost:8080/api/cart/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  })
    .then(response => {
      if (!response.ok) throw new Error("Failed to add to cart");
      return response.json();
    })
    .then(data => {
      alert("Added to cart!");
    })
    .catch(error => {
      console.error("Error adding to cart:", error);
      alert("Error adding to cart");
    });
}