function registerCallback(registrationId) {
	console.log('registrationId: ', registrationId);
	if (chrome.runtime.lastError)
		return;

	chrome.storage.local.set({registrationId: registrationId});
	chrome.storage.local.set({registered: true});
}

chrome.runtime.onInstalled.addListener(function() {
	chrome.storage.local.get("registered", function(result) {
		console.log('registered: ', result["registered"]);
		// If already registered, bail out.
		if (result["registered"])
			return;
		
		// Up to 100 senders are allowed.
		var senderIds = ["12113745422"];
		chrome.gcm.register(senderIds, registerCallback);
	});
});

chrome.gcm.onMessage.addListener(function(message) {
	console.log('New message: ', message);
});