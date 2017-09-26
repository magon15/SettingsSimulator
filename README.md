# SettingsSimulator
A Settings app prototype for Android

What you could learn from this code:
- How to use SharedPreferences to save user preferences, for data will be stored even upon device shutdown and it doesn't require a database.
- How to make an app respond to Broadcast Intents (Airplane mode and checking for headset connection in this case)
- How to use ViewPagers
- and more...


Known issues:
- Turning from portrait to landscape mode doesn't recreate Fragments in FragmentStatePagerAdapter, therefore the callback interface returns null and causes bugs.
