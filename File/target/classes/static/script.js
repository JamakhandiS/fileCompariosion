document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('compareForm').addEventListener('submit', async function (e) {
        e.preventDefault();

        const file1 = document.getElementById('file1').files[0];
        const file2 = document.getElementById('file2').files[0];

        if (!file1 || !file2) {
            alert("Please upload both files!");
            return;
        }

        const formData = new FormData();
        formData.append('file1', file1);
        formData.append('file2', file2);

        try {
            const response = await fetch('/compare', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const result = await response.text();
            document.getElementById('result').innerText = result;

        } catch (error) {
            document.getElementById('result').innerText = "Error comparing documents.";
            console.error(error);
        }
    });
});
