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

function changeVideoTime(video, time){
	var currentTime = video.currentTime;
	console.debug('Current time: ', currentTime);
	currentTime += time;
	currentTime = (currentTime < 0) ? 0 : currentTime;
	video.currentTime = currentTime;
	console.debug('New time: ', currentTime);
}

function isPlaying(video){
	return video.currentTime > 0 && !video.paused && !video.ended;
}

function changeVolume(video, changedVolume) {
	var currentVolume = video.volume;
	console.debug('Current volume: ', currentVolume);
	currentVolume += changedVolume;
	currentVolume = (currentVolume > 1) ? 1 : currentVolume;
	currentVolume = (currentVolume < 0) ? 0 : currentVolume;
	video.volume = currentVolume;
	console.debug('New volume: ', currentVolume);
}

chrome.runtime.onMessage.addListener(
	function(request, sender, sendResponse) {
		console.debug('Receving new message.');
		var video = document.querySelector("video");
		
		if (video) {
			switch (request.command){
				case INCREASE_VOLUME:					
					changeVolume(video, 0.1);
					break;
				case REDUCE_VOLUME:
					changeVolume(video, -0.1);
					break;
				case MUTE:
					video.muted = !video.muted;
					break;
				case LAST_30S:
					changeVideoTime(video, -30);
					break;
				case NEXT_30S:
					changeVideoTime(video, 30);
					break;
				case REWIND:
					changeVideoTime(video, -10);
					break;	
				case FORWARD:	
					changeVideoTime(video, 10);
					break;
				case PLAY:
					if (isPlaying(video)){
						video.pause();
					} else {
						video.play();
					}
					break;
				default: break;
			}
		}
	});
