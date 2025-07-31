document.addEventListener("DOMContentLoaded", function () {
  loadCartItems();
});

function loadCartItems() {
  fetch("http://localhost:8080/api/cart")
    .then(response => {
      if (!response.ok) {
        throw new Error("Failed to fetch cart items.");
      }
      return response.json();
    })
    .then(cartItems => {
      const cartItemsDiv = document.getElementById("cart-items");
      const totalPriceElement = document.getElementById("total-price");
      cartItemsDiv.innerHTML = "";

      if (cartItems.length === 0) {
        cartItemsDiv.innerHTML = "<p>Your cart is empty.</p>";
        totalPriceElement.innerText = "Total: ₹0";
        return;
      }

      let totalPrice = 0;

      cartItems.forEach(item => {
        console.log("Cart item structure:", item); // ✅ debug print
        totalPrice += item.price * item.quantity;

        const productCard = document.createElement("div");
        productCard.classList.add("product-card");
        productCard.style.marginBottom = "20px";

        productCard.innerHTML = `
          <img src="/images/${item.image}" alt="${item.productName}" width="100" />
          <div>
            <h3>${item.productName}</h3>
            <p>Price: ₹${item.price}</p>
            <p>Quantity: ${item.quantity}</p>
            <button type="button" class="order-btn" data-product-id="${item.productId}" style="
              background-color: green;
              color: white;
              padding: 8px;
              border: none;
              border-radius: 5px;
              cursor: pointer;
              margin-right: 10px;">Order Now</button>
            <button type="button" class="remove-btn" data-id="${item.id}" style="
              background-color: red;
              color: white;
              padding: 8px;
              border: none;
              border-radius: 5px;
              cursor: pointer;">Remove</button>
          </div>
        `;

        cartItemsDiv.appendChild(productCard);
      });

      totalPriceElement.innerText = `Total: ₹${totalPrice}`;

      // ✅ Attach Remove button listeners
      document.querySelectorAll(".remove-btn").forEach(button => {
        button.addEventListener("click", function () {
          const productId = this.getAttribute("data-id");
          deleteCartItem(productId);
        });
      });

      // ✅ Attach Order button listeners
      document.querySelectorAll(".order-btn").forEach(button => {
        button.addEventListener("click", function () {
          const productId = this.getAttribute("data-product-id");
          orderNow(productId);
        });
      });
    })
    .catch(error => {
      console.error("Error:", error);
      document.getElementById("cart-items").innerHTML = "<p>Error loading cart items.</p>";
    });
}

function deleteCartItem(productId) {
  fetch(`http://localhost:8080/api/cart/${productId}`, {
    method: "DELETE"
  })
    .then(response => {
      if (!response.ok) {
        throw new Error("Failed to delete cart item.");
      }
      loadCartItems();
    })
    .catch(error => {
      console.error("Error deleting item:", error);
    });
}

async function orderNow(productId) {
  console.log("Placing order for productId:", productId); // ✅ debug print

  const response = await fetch("http://localhost:8080/api/orders/place", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      userId: 8,
      productIds: [parseInt(productId)]
    })
  });

  if (!response.ok) {
    const errorText = await response.text(); // ✅ Show actual error from backend
    console.error("Order failed:", errorText);
    alert("Order failed.\n" + errorText);
    return;
  }

  const order = await response.json();
  if (order && order.id) {
    alert("Order placed successfully! Downloading PDF...");
    window.open(`http://localhost:8080/api/orders/${order.id}/pdf`, "_blank");
  } else {
    alert("Order failed.");
  }
}