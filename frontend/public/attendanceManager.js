const socket = io.connect({transports: ['websocket']});

socket.on('queue', displayQueue);
socket.on('motd', displayMessage);

function displayMessage(newMOTD) {
    document.getElementById("motd").innerHTML = newMOTD;
}

function displayQueue(queueJSON) {
    const queue = JSON.parse(queueJSON);
    let formattedQueue = "<div class=\"details\"> <p>No one is currently checked-in to the lab.</p> </div>";
    for (const student of queue) {
        formattedQueue += student['username'] + " has been in the lab since " + student['timestamp'] + "<br/>"
    }
    document.getElementById("checkedInQueue").innerHTML = formattedQueue;
}



function enterPersonNum() {
    let name = document.getElementById("NumEnter").value;
    socket.emit("clock_ubnum", name);
    document.getElementById("name").value = "";
}


var options = {
    bottom: '64px',              // default: '32px'
    right: '32px',              // default: '32px'
    left: 'unset',                // default: 'unset'
    time: '0.5s',                // default: '0.3s'
    mixColor: '#f2f2f2',            // default: '#fff'
    backgroundColor: '#fff',     // default: '#fff'
    buttonColorDark: '#100f2c',  // default: '#100f2c'
    buttonColorLight: '#f2f2f2',    // default: '#fff'
    saveInCookies: true,         // default: true,
    label: '🌓',                 // default: ''
    autoMatchOsTheme: true       // default: true
}
const darkmode = new Darkmode(options);
darkmode.showWidget();