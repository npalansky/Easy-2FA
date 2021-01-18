# Easy 2FA

This project aims to makes it more convinient to use Two Factor Authentication without disrupting your workflow by automatically copying the codes to your clipboard on all devices without compimising on security. 
 
It consists of an Android app which monitors SMS in order to recognize security codes.  When a code is received, it is automatically copied to the clipboard.  

### Future works: 
* Mac Client
    * develop a desktop service and securly push the notification to it when observed on mobile. 
    * It should allow someone to "trust" a device and have 2fa codes automatically copied on that device as long as it remains trusted and the 2FA phone remains online. If the phone or trusted device is lost, you should be able to de-activate the Easy 2FA link.
