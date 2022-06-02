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

  const allRangeInputs = document.querySelectorAll('input[type="range"]');

  const downloadedBuffers = [];
  const audioContext = new AudioContext();

  let audioSource;

  let currentInterval;

  const playTrack = async (event) => {
    const song = event.target.closest('.song');
    const range = song.querySelector('input[type="range"]');
    const songTimePassed = song.querySelector('.song-time.passed');
    const songTimeLeft = song.querySelector('.song-time.left');

    const songId = song.dataset['song'];
    const startFrom = range.value;

    const duration = +song.dataset['duration'];

    let currentArrayBuffer;
    let currentAudioBuffer;

    if (audioSource) {
      stopTrack();
    }

    if (downloadedBuffers[songId]) {
      currentAudioBuffer = downloadedBuffers[songId];
    } else {
      currentArrayBuffer = await (await fetch(`/composition/play?id=${songId}`)).arrayBuffer();
      currentAudioBuffer = await audioContext.decodeAudioData(currentArrayBuffer);
      downloadedBuffers[songId] = currentAudioBuffer;
    }

    audioSource = audioContext.createBufferSource();
    audioSource.buffer = currentAudioBuffer;
    audioSource.connect(audioContext.destination);
    audioSource.start(0, startFrom);

    currentInterval = setInterval(() => {
      range.value = +range.value + 1;

      const currentValue = +range.value;
      const left = duration - currentValue;
      const formattedCurrent = formatNumber(currentValue);
      const formattedLeft = '-' + formatNumber(left);

      songTimePassed.innerText = formattedCurrent;
      songTimeLeft.innerText = formattedLeft;

      if (currentValue >= duration) {
        clearInterval(currentInterval);
        range.value = 0;
        song.querySelector('.button-pause').classList.add('hide');
        song.querySelector('.button-play').classList.remove('hide');
      }
    }, 1000);
  }

  const stopTrack = (event) => {
    audioSource?.stop();

    if (currentInterval) {
      clearInterval(currentInterval);
    }
  }

  const formatNumber = (number) => {
    const minute = 60;
    let seconds = number % minute;
    let minutes = div(number, minute);

    if (seconds < 10) {
      seconds = '0' + seconds;
    }

    if (minutes < 10) {
      minutes = '0' + minutes;
    }

    return `${minutes}:${seconds}`;
  }

  const div = (val, by) => {
    return (val - val % by) / by;
  }

  const hideOthersStopButtons = (currentPauseButton) => {
    return () => {
      for (let i = 0; i < allPauseButtons.length; i++) {
        if (allPauseButtons[i] !== currentPauseButton) {
          allPauseButtons[i].classList.add('hide');
          allPlayButtons[i].classList.remove('hide');
        }
      }
    }
  }

  genresFilter.addEventListener('click', () => {
    genresFilter.classList.toggle('closed');
  });

  dateFilter.addEventListener('click', () => {
    dateFilter.classList.toggle('closed');
  });

  for (let i = 0; i < allPlayButtons.length; i++) {
    let currentPlayButton = allPlayButtons[i];
    let currentPauseButton = allPauseButtons[i];

    allPlayButtons[i].addEventListener('click', playTrack);

    allPlayButtons[i].addEventListener('click', () => {
      currentPlayButton.classList.add('hide');
      currentPauseButton.classList.remove('hide');
    });

    currentPlayButton.addEventListener('click', hideOthersStopButtons(currentPauseButton));
  }

  for (let i = 0; i < allPauseButtons.length; i++) {
    let currentPlayButton = allPlayButtons[i];
    let currentPauseButton = allPauseButtons[i];

    allPauseButtons[i].addEventListener('click', stopTrack);

    allPauseButtons[i].addEventListener('click', () => {
      currentPauseButton.classList.add('hide');
      currentPlayButton.classList.remove('hide');
    })
  }

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

  allRangeInputs.forEach(input => {
    input.addEventListener('input', (event) => {
      const range = event.target;
      const song = range.closest('.song');
      const duration = song.dataset['duration'];
      const currentStopButton = song.querySelector('.button-pause');
      const currentPlayButton = song.querySelector('.button-play');
      const songTimePassed = song.querySelector('.song-time.passed');
      const songTimeLeft = song.querySelector('.song-time.left');

      if (currentPlayButton.classList.contains('hide')) {
        currentPlayButton.classList.add('hide');
        currentStopButton.classList.remove('hide');

        hideOthersStopButtons(currentStopButton)();
        stopTrack(event);
        playTrack(event);
      } else {
        const currentValue = +range.value;
        const left = duration - currentValue;
        const formattedCurrent = formatNumber(currentValue);
        const formattedLeft = '-' + formatNumber(left);

        songTimePassed.innerText = formattedCurrent;
        songTimeLeft.innerText = formattedLeft;
      }
    })
  });
}