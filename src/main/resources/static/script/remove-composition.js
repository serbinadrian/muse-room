window.addEventListener('DOMContentLoaded', () => {
    const deleteButtons = document.querySelectorAll('.own-music-buttons-delete');

    deleteButtons.forEach(deleteButton => {
        deleteButton.addEventListener('click', async (event) => {
            const songId = event.target.closest('.own-music').dataset['id'];
            const formData = new FormData();

            formData.set('id', songId);

            await fetch('/composition/remove', {
                method: 'DELETE',
                body: formData
            });

            location.reload();
        });
    });
});