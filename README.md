# Simba

This is a Tamagotchi android app featuring the greatest cat ever, Simba.

Play and take care of Simba.

This Android app follows MVVM architecture + Multiplatform Settings for the persistence

Will run on android but IOS compatability in mind for the future.

## TODO
### Features
 - [ ] Cleanup entire UI
 - [ ] Finish extras
   - [ ] Debug mode (requires code, this gives +- button for stats) (future)
   - [ ] Bundle game icon selection options to choose from (future)
   - [ ] * Gallery view (See if not all, most Simba assets and maybe more secret assets)
 - [ ] * Add Simba death (If health == 0, state should always be DEAD and show a Simba angel asset)
 - [ ] * On death, show a restart button
 - [ ] * Change decay as to where if all stats are >95, health goes up on the decay (passive healing)
 - [ ] * Add ability to pet Simba (Visual FX like sparkles, raises happiness)
 - [ ] * Implement internationalization based on language chosen
 ### Fixes
 - [ ] * Load/Saving bug for Simba default stat change (This is a Simba state version issue)