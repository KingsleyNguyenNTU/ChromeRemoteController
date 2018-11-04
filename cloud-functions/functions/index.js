const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.sendMessage = functions.https.onCall((data, context) => {
      console.log('Data: ', data);
	  const receiverTokenId = data.receiverTokenId;
	  const command = data.command;
	  const optionalParameter = data.optionalParameter;
	  
      console.log('Request content', receiverTokenId, command, optionalParameter);
      
      var message = {
    		  data: {
    			  command: command,
    			  optionalParameter: optionalParameter
    		  },
    		  token: receiverTokenId
    		};
      
      admin.messaging().send(message).then((response) => {
        // Response is a message ID string.
        console.log('Successfully sent message:', response);
        return true;
      }).catch((error) => {
        console.log('Error sending message:', error);
        return false;
      });
});
