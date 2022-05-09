window.onload = () => {
  let main = document.getElementById('main');
  let nav = document.getElementById('nav');
  let modal = document.getElementById('modal');
  let closeModal = document.getElementById('close');

  let genresFilter = document.getElementById('filter-genres');
  let dateFilter = document.getElementById('filter-date');

  let allRentButtons = document.getElementsByClassName('button-rent');
  let allBuyButtons = document.getElementsByClassName('button-buy');
  let allPlayButtons = document.getElementsByClassName('button-play');
  let allPauseButtons = document.getElementsByClassName('button-pause');

  let allFocusButtons = [...allRentButtons, ...allBuyButtons];

  genresFilter.addEventListener('click', () => {
    genresFilter.classList.toggle('closed');
  });

  dateFilter.addEventListener('click', () => {
    dateFilter.classList.toggle('closed');
  });

  for (var i = 0; i < allPlayButtons.length; i++) {
    let currentPlayButton = allPlayButtons[i];
    let currentPauseButton = allPauseButtons[i];
    allPlayButtons[i].addEventListener('click', () => {
      currentPlayButton.classList.add('hide');
      currentPauseButton.classList.remove('hide');
    })
  }

  for (var i = 0; i < allPauseButtons.length; i++) {
    let currentPlayButton = allPlayButtons[i];
    let currentPauseButton = allPauseButtons[i];
    allPauseButtons[i].addEventListener('click', () => {
      currentPauseButton.classList.add('hide');
      currentPlayButton.classList.remove('hide');
    })
  }

  for (var i = 0; i < allFocusButtons.length; i++) {
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
  })

}
