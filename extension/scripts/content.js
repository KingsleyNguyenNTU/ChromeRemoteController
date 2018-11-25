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
console.log('Content script running.');

chrome.runtime.onMessage.addListener(
	function(request, sender, sendResponse) {
		console.log('Receving new message.');
		var video = document.querySelector("video");
		
		if (video) {
			switch (request.command){
				case INCREASE_VOLUME:					
					var currentVolume = video.volume;
					console.log('Current volume: ', currentVolume);
					currentVolume += 0.1;
					currentVolume = (currentVolume > 1) ? 1 : currentVolume;
					video.volume = currentVolume;
					console.log('New volume: ', currentVolume);
					break;
				case REDUCE_VOLUME:
					var currentVolume = video.volume;
					console.log('Current volume: ', currentVolume);
					currentVolume -= 0.1;
					currentVolume = (currentVolume < 0) ? 0 : currentVolume;
					video.volume = currentVolume;
					console.log('New volume: ', currentVolume);
					break;
				case MUTE:
					console.log('Is Mute: ', video.muted);
					video.muted = !video.muted;
					console.log('Set Mute: ', video.muted);
					break;
				default: break;
			}
		}
	});
