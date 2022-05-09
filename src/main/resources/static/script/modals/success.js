window.onload = () => {
  let closeModal = window.parent.document.getElementById('close');
  let closeMessage = document.getElementById('close-message');

  closeMessage.addEventListener('click', ()=>{
    const event = new Event('build');
    closeModal.dispatchEvent(event);
  });
}
