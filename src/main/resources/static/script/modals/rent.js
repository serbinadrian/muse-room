window.onload = () => {


    let listenCount = document.getElementById('listenCount');
    let add = document.getElementById('add');
    let sub = document.getElementById('sub');

    let currentValue = listenCount.value;

    function setFormattedValue() {
        listenCount.value = currentValue.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",") + " прослушиваний";
    }

    setFormattedValue();
    currentValue   = 1000;

    add.addEventListener('click', () => {
        if (currentValue < 10000) {
            currentValue += 1000;
        } else if (currentValue < 100000) {
            currentValue += 10000;
        } else if (currentValue < 1000000) {
            currentValue += 100000;
        } else {
            currentValue += 1000000;
        }
        setFormattedValue();
    });

    sub.addEventListener('click', () => {
        if (currentValue <= 10000) {
            currentValue -= 1000;
        } else if (currentValue <= 100000) {
            currentValue -= 10000;
        } else if (currentValue <= 1000000) {
            currentValue -= 100000;
        } else {
            currentValue -= 1000000;
        }
        if (currentValue < 1000) {
            currentValue = 1000;
        }
        setFormattedValue();
    });
}
