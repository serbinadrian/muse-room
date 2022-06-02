window.addEventListener('DOMContentLoaded', () => {
    const uploadDiv = document.querySelector('.upload-new-music');
    const uploadForm = document.querySelector('.load-composition');
    const fileInput = uploadForm.querySelector('input[type="file"]');

    uploadDiv.addEventListener('click', () => {
        fileInput.click();
    });

    fileInput.addEventListener('input', async () => {
        if (fileInput.files.length) {
            const formData = new FormData(uploadForm);

            await fetch('/composition/add', {
                method: "POST",
                body: formData
            });

            window.location=window.location;

        }
    });
});