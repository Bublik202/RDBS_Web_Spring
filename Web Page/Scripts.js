// Функция фильтрации товаров по категориям
document.querySelectorAll('.filter-btn').forEach(button => {
    button.addEventListener('click', () => {
        const category = button.getAttribute('data-category');
        document.querySelectorAll('.product').forEach(product => {
            if (category === 'all' || product.getAttribute('data-category') === category) {
                product.style.display = 'block';
            } else {
                product.style.display = 'none';
            }
        });
    });
});

// Функция добавления товара в корзину
document.querySelectorAll('.add-to-cart').forEach(button => {
    button.addEventListener('click', () => {
        const name = button.getAttribute('data-name');
        const price = parseInt(button.getAttribute('data-price'));
        
        // Добавляем товар в корзину
        const cartItems = document.getElementById('cart-items');
        const listItem = document.createElement('li');
        listItem.textContent = `${name} - ${price}$`;
        cartItems.appendChild(listItem);

        // Обновляем итоговую сумму
        const total = document.getElementById('total');
        const currentTotal = parseInt(total.textContent.replace('Итого: ', '').replace('$', ''));
        total.textContent = `Итого: ${currentTotal + price}$`;
    });
});
