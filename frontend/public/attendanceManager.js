
const socket = io('http://localhost:8080');

socket.on('clockedIn_update', updateQueue);
socket.on('motd',  displayMOTD);
//Socket Debug Tool Start
socket.on("*",function(event,data) {
    console.log("New event: [" + event + "] " + data);
})
var onevent = socket.onevent;
socket.onevent = function (packet) {
    var args = packet.data || [];
    onevent.call (this, packet);    // original call
    packet.data = ["*"].concat(args);
    onevent.call(this, packet);      // additional call to catch-all
};
//End Debug tool

const months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
const days   = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
var queue = ""


function updateQueue(queueJSON) {
    queue = JSON.parse(queueJSON);
    displayQueue()
}

function displayQueue() {
    if(queue == "")  document.getElementById("checkedInQueue").innerHTML = "<div class=\"details\"> <p>No one is currently checked-in to the lab.</p> </div>";
    else {
        let formattedQueue = "";
        for (const student of queue) {
            const unix_timestamp = student['timestamp'];
            const dateObj = new Date(unix_timestamp * 1000)
            const formattedDate = days[dateObj.getDay()] + " " + dateObj.getHours() + ':' + dateObj.getMinutes()

            const username = student['username'];
            formattedQueue += username + " has been in the lab since " + formattedDate + "<br/>"

        }
        document.getElementById("checkedInQueue").innerHTML = formattedQueue;
    }
}

function displayMOTD(newMOTD) {
    document.getElementById("motd").innerHTML = newMOTD;
}



function enterPersonNum() {
    let personNum = parseInt(document.getElementById("NumEnter").value);
    if(!isNaN(personNum) && personNum.toString().length == 8) {
        socket.emit("clock_ubnum", personNum);
        document.getElementById("NumEnter").value = "";
    } else {
        personNumEntryPopUp("Incorrect format for a UB Person Number!")
    }

}

function personNumEntryPopUp(msg = "Error") {
    let popup = document.getElementById("personNumPopUp");
    popup.innerHTML = msg
    popup.classList.toggle("show");
}

var options = {
    bottom: '64px',                  // default: '32px'
    right: '32px',                   // default: '32px'
    left: 'unset',                   // default: 'unset'
    time: '0.5s',                    // default: '0.3s'
    mixColor: '#f2f2f2',             // default: '#fff'
    backgroundColor: '#fff',         // default: '#fff'
    buttonColorDark: '#100f2c',      // default: '#100f2c'
    buttonColorLight: '#f2f2f2',     // default: '#fff'
    saveInCookies: true,             // default: true,
    label: '🌓',                     // default: ''
    autoMatchOsTheme: true          // default: true
}
const darkmode = new Darkmode(options);
darkmode.showWidget();