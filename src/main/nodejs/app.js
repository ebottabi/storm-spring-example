var context = require('zeromq')

var express = require('express');

var sender = context.createSocket('push')
sender.bindSync("tcp://*:5555")

var app = express.createServer();

app.get('/ec_p/1_0_0/addItemViewEvent', function(req, res) {

	sendMessage(req, "item", sender);

	res.send('success');
});

app.get('/ec_p/1_0_0/addCartViewEvent', function(req, res) {

	sendMessage(req, "cart", sender);

	res.send('success');
});

app.get('/ec_p/1_0_0/addOrderViewEvent', function(req, res) {

	sendMessage(req, "order", sender);

	res.send('success');
});

function sendMessage(req, eventType, socket) {
	var eventString = req.param("eventString");
	var message = "{'eventType': '" + eventType + "', 'eventString': " + eventString + "}";
	socket.send(message);
}

app.listen(80);
