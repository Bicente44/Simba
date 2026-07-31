# Simba

This is a Tamagotchi android app featuring the greatest cat ever, Simba.

Play and take care of Simba.

This Android app follows MVVM architecture + Multiplatform Settings for the persistence

Will run on android but IOS compatability in mind for the future.

## TODO
* = Required for MVP
### Features
 - [ ] Cleanup entire UI
 - [ ] Debug mode (requires code, this gives +- button for stats) (future)
 - [ ] Bundle game icon selection options to choose from (future)
 - [x] Simba talking/text bubble
### Assets
 - [ ] Remake action icons
   - [ ] Food
   - [ ] Play
   - [ ] Sleep
   - [ ] Clean
 ### Fixes
 - [x] * Load/Saving bug for Simba default stat change (This is a Simba state version issue)
   Version ID, load default data on missing fields, remove data on extra fields.