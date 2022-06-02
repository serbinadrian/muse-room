window.onload = () => {

    let main = document.getElementById('main');
    let nav = document.getElementById('nav');
    let modal = document.getElementById('modal');
    let closeModal = document.getElementById('close');

    let ownSwitch = document.getElementById('own-switch');
    let renSwitch = document.getElementById('rent-switch');
    let boughtSwitch = document.getElementById('bought-switch');

    let own = document.getElementById('own');
    let rent = document.getElementById('rent');
    let bought = document.getElementById('bought');

    let currentBlock = own;
    let currentSwitch = ownSwitch;

    let allFocusButtons  = document.getElementsByClassName('focusB');

    ownSwitch.addEventListener('click', () => {
        currentSwitch.classList.remove('active');
        ownSwitch.classList.add('active');
        currentSwitch = ownSwitch;
        currentBlock.classList.add('hide');
        own.classList.remove('hide');
        currentBlock = own;
    });
    renSwitch.addEventListener('click', () => {
        currentSwitch.classList.remove('active');
        renSwitch.classList.add('active');
        currentSwitch = renSwitch;
        currentBlock.classList.add('hide');
        rent.classList.remove('hide');
        currentBlock = rent;
    });
    boughtSwitch.addEventListener('click', () => {
        currentSwitch.classList.remove('active');
        boughtSwitch.classList.add('active');
        currentSwitch = boughtSwitch;
        currentBlock.classList.add('hide');
        bought.classList.remove('hide');
        currentBlock = bought;
    });

    for (let i = 0; i < allFocusButtons.length; i++) {
        allFocusButtons[i].addEventListener('click', () => {
            nav.classList.add('unfocused');
            main.classList.add('unfocused');
            modal.classList.remove('hide');
        });
    }

    closeModal.addEventListener('click', () => {
        modal.classList.add('hide');
        nav.classList.remove('unfocused');
        main.classList.remove('unfocused');
        window.location=window.location;
    })
}