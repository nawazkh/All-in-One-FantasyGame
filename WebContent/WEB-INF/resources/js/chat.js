var endPointURL = "ws://" + window.location.host + "/All-In-One-FantasyGame/chat";

var chatClient = null;

function connect () {
	alert(1);
    chatClient = new WebSocket(endPointURL);
    chatClient.onmessage = function (event) {
        var messagesArea = document.getElementById("messages");
        var jsonObj = JSON.parse(event.data);
        var message = jsonObj.user + ": " + jsonObj.message + "\r\n";
        messagesArea.value = messagesArea.value + message;
        messagesArea.scrollTop = messagesArea.scrollHeight;
    };
}

function disconnect () {
    chatClient.close();
}

function sendMessage() {
    var user = document.getElementById("userName").value.trim();
    if (user === "")
        alert ("Please enter your name!");
    
    var inputElement = document.getElementById("messageInput");
    var message = inputElement.value.trim();
    if (message !== "") {
        var jsonObj = {"user" : user, "message" : message};
        chatClient.send(JSON.stringify(jsonObj));
        inputElement.value = "";
    }
    inputElement.focus();
}