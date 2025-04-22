document.addEventListener('DOMContentLoaded', function () {

    const displayResult = (elementId, data) => {
        document.getElementById(elementId).textContent = JSON.stringify(data, null, 2);
    };

    // 1. 카테고리별 최저가 조회
    document.getElementById('btnFetchCategoryAll').addEventListener('click', async function () {
        try {
            const response = await fetch('/api/stats/category/all');
            const data = await response.json();
            displayResult('resultCategoryAll', data);
        } catch (error) {
            displayResult('resultCategoryAll', { error: error.messages });
        }
    });

    // 2. 특정 카테고리 조회
    document.getElementById('btnFetchCategorySingle').addEventListener('click', async function () {
        const category = document.getElementById('inputCategory').value;
        try {
            const response = await fetch(`/api/stats/category?name=${encodeURIComponent(category)}`);
            const data = await response.json();
            displayResult('resultCategorySingle', data);
        } catch (error) {
            displayResult('resultCategorySingle', { error: error.messages });
        }
    });

    // 3. 브랜드 최저가 조회
    document.getElementById('btnFetchBrandStats').addEventListener('click', async function () {
        try {
            const response = await fetch('/api/stats/brand');
            const data = await response.json();
            displayResult('resultBrandStats', data);
        } catch (error) {
            displayResult('resultBrandStats', { error: error.messages });
        }
    });

    // 4. 브랜드 등록
    document.getElementById('formCreateBrand').addEventListener('submit', async function (e) {
        e.preventDefault();
        const brandName = document.getElementById('createBrandName').value;
        const categories = ['TOPS', 'OUTERWEAR', 'PANTS', 'SNEAKERS', 'BAG', 'HAT', 'SOCKS', 'ACCESSORIES'];
        const productList = categories.map(category => ({
            category: category,
            price: parseInt(document.getElementById(`create_${category}`).value, 10)
        }));
        const payload = {brandName, productList};
        try {
            const response = await fetch('/api/product', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload)
            });
            const data = await response.json();
            displayResult('resultCreateBrand', data);
        } catch (error) {
            displayResult('resultCreateBrand', { error: error.messages });
        }
    });

    // 5. 상품 수정
    document.getElementById('formUpdateProduct').addEventListener('submit', async function (e) {
        e.preventDefault();
        const payload = {
            brandName: document.getElementById('updateBrandName').value,
            category: document.getElementById('updateCategory').value,
            price: parseInt(document.getElementById('updatePrice').value, 10)
        };

        try {
            const response = await fetch('/api/product', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload)
            });
            const data = await response.json();
            displayResult('resultUpdateProduct', data);
        } catch (error) {
            displayResult('resultUpdateProduct', { error: error.messages });
        }
    });

    // 6. 브랜드 삭제
    document.getElementById('formDeleteBrand').addEventListener('submit', async function (e) {
        e.preventDefault();
        const brandName = document.getElementById('deleteBrandName').value;
        try {
            const response = await fetch(`/api/product?name=${encodeURIComponent(brandName)}`, {
                method: 'DELETE'
            });
            displayResult('resultDeleteBrand', { message: '삭제 완료' });
        } catch (error) {
            displayResult('resultDeleteBrand', { error: error.messages });
        }
    });
});
