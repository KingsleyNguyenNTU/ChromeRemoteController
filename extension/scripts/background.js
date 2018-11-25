const SENDER_ID = '12113745422';

//remote command name
const TURN_OFF = 'TURN_OFF';
const INCREASE_VOLUME = 'INCREASE_VOLUME';
const REDUCE_VOLUME = 'REDUCE_VOLUME';
const MUTE = 'MUTE';
const NEW_TAB = 'NEW_TAB';
const NEXT_TAB = 'NEXT_TAB';
const PREVIOUS_TAB = 'PREVIOUS_TAB';
const LAST_30S = 'LAST_30S';
const NEXT_30S = 'NEXT_30S';
const REWIND = 'REWIND';
const FORWARD = 'FORWARD';
const PLAY = 'PLAY';

function registerCallback(registrationId) {
	console.log('registrationId: ', registrationId);
	if (chrome.runtime.lastError)
		return;

	chrome.storage.local.set({registrationId: registrationId});
	chrome.storage.local.set({registered: true});
}

chrome.runtime.onInstalled.addListener(function() {
	chrome.storage.local.get('registered', function(result) {
		// If already registered, bail out.
		if (result['registered'])
			return;
		
		var senderIds = [SENDER_ID];
		chrome.gcm.register(senderIds, registerCallback);
	});
});

chrome.gcm.onMessage.addListener(function(message) {
	console.log('New message: ', message);
	
	var command = message && message.data && message.data.command;
	switch (command){
		case TURN_OFF:
			chrome.tabs.getSelected(function(tab) {
			    chrome.tabs.remove(tab.id, function() { });
			});
			break;
		case INCREASE_VOLUME:
		case REDUCE_VOLUME:
		case MUTE:
		case LAST_30S:
		case NEXT_30S:
		case REWIND:	
		case FORWARD:	
		case PLAY:	
			chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
				chrome.tabs.sendMessage(tabs[0].id, {command: command});
			});
			break;			
		default: break;
	}
});