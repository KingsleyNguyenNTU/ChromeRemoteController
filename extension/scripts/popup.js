chrome.storage.local.get("registrationId", function(result) {
	console.log('registrationId: ' + result["registrationId"]);
	// If already registered, bail out.
	new QRCode(document.getElementById("qrcode"),result["registrationId"]);
});