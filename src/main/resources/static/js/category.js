document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search);
  const category = params.get('category');

  // Set category title
  document.getElementById('category-title').innerText = category + " Products";

  // Fetch products from backend using correct URL template
  fetch(`/api/products/category/${category}`)
    .then(response => response.json())
    .then(products => {
      const container = document.getElementById('category-products');
      container.innerHTML = "";

      if (products.length === 0) {
        container.innerHTML = "<p>No products found in this category.</p>";
        return;
      }

      products.forEach(product => {
        const card = document.createElement('div');
        card.className = 'product-card';

        const imageName = product.image || "default.png";

        card.innerHTML = `
          <img src="images/${imageName}" alt="${product.name}" />
          <h4>${product.name}</h4>
          <p>₹${product.price}</p>
          <a href="product-details.html?id=${product.id}">View Details</a>
        `;

        container.appendChild(card);
      });
    })
    .catch(error => {
      console.error('Error loading products:', error);
    });
});