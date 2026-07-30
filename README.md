# Simba

This is a Tamagotchi android app featuring the greatest cat ever, Simba.

Play and take care of Simba.

This Android app follows MVVM architecture + Multiplatform Settings for the persistence

Will run on android but IOS compatability in mind for the future.

## TODO
* = Required for MVP
### Features
 - [ ] Cleanup entire UI
 - [ ] Finish extras
   - [ ] Debug mode (requires code, this gives +- button for stats) (future)
   - [ ] Bundle game icon selection options to choose from (future)
   - [ ] * Gallery view (See if not all, most Simba assets and maybe more secret assets)
 - [x] * Add Simba death (If health == 0, state should always be DEAD and show a Simba angel asset)
 - [x] * On death, show a restart button
 - [x] * Change decay as to where if all stats are >85, health goes up on the decay (passive healing)
 - [ ] * Add ability to pet Simba (Visual FX like sparkles, raises happiness)
 - [ ] * Implement internationalization based on language chosen
### Assets
 - [x] Left icon (Back button)
 - [ ] Remake action icons
   - [ ] Food
   - [ ] Play
   - [ ] Sleep
   - [ ] Clean
 - [x] Dead Simba
 ### Fixes
 - [ ] * Load/Saving bug for Simba default stat change (This is a Simba state version issue)