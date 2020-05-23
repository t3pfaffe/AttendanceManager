
const socket = io('http://localhost:8080');

socket.on('clockedIn_update', updateQueue);
socket.on('motd',  displayMOTD);
//Socket Debug Tool Start
socket.on("*",function(event,data) {
    console.log("New event: [" + event + "] " + data);
})
const onevent = socket.onevent;
socket.onevent = function (packet) {
    var args = packet.data || [];
    onevent.call (this, packet);    // original call
    packet.data = ["*"].concat(args);
    onevent.call(this, packet);      // additional call to catch-all
};
//End Debug tool

const months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
const days   = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
let queue = "";

/**
 * @param queueJSON change queue with JSON argument
 */
function updateQueue(queueJSON) {
    queue = JSON.parse(queueJSON);
    displayQueue()
}

/**
 * Displays Queue
 * (can be called periodically)
 */
function displayQueue() {
    if(queue == "")  document.getElementById("checkedInQueue").innerHTML = "<div class=\"details\"> <p>No one is currently checked-in to the lab.</p> </div>";
    else {
        const oldQueue = document.getElementById("checkedInQueue");
        let newQueue = document.createElement("div");

        for (const student of queue) {
            const unix_timestamp = student['timestamp'];
            const dateObj = new Date(unix_timestamp * 1000)

            const formattedDate = days[dateObj.getDay()] + " " + dateObj.getHours() + ':' + dateObj.getMinutes()
            const username = student['username'];

            const nameSpan = document.createElement("span");
            nameSpan.innerText = username

            const textDiv = document.createElement("div");
            textDiv.innerText = " has been in the lab since "

            const timeSpan = document.createElement("time");
            timeSpan.innerText = formattedDate

            const infoDiv = document.createElement("div");
            infoDiv.classList.add("details")
            infoDiv.appendChild(nameSpan)
            infoDiv.appendChild(textDiv)
            infoDiv.appendChild(timeSpan)

            const queueEntry = document.createElement("div");
            queueEntry.classList.add("queueObject")
            queueEntry.appendChild(infoDiv)

            newQueue.appendChild(queueEntry)
        }
        oldQueue.parentNode.replaceChild(newQueue, oldQueue);
    }
}

/**
 * @param newMOTD change motd to new updated one.
 */
function displayMOTD(newMOTD) {
    document.getElementById("motd").innerHTML = newMOTD;
}


/**
 * Send server message with new clock-on
 */
function clockInPersonNum() {
    let personNum = parseInt(document.getElementById("NumEnter").value);
    if(!isNaN(personNum) && personNum.toString().length == 8) {
        socket.emit("clock_ubnum", personNum);
        document.getElementById("NumEnter").value = "";
    } else {
        personNumEntryPopUp("Incorrect format for a UB Person Number!")
    }
}

function clockOutPersonNum() {

}

/**
 * @param msg to be inside of popup error for PersonNumEntry
 */
function personNumEntryPopUp(msg = "Error") {
    let popup = document.getElementById("personNumPopUp");
    popup.innerHTML = msg
    popup.classList.toggle("show");
}


/**
 * Options for darkmode widget
 * @type {{backgroundColor: string, saveInCookies: boolean, left: string, bottom: string, buttonColorDark: string, right: string, time: string, label: string, autoMatchOsTheme: boolean, buttonColorLight: string, mixColor: string}}
 */
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