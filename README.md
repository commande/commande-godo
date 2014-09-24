commande-godo
=============
A simple todo-list application for Assignment 1 of CMPUT 301 Fall 2014

External Sources
----------------
This application makes no use of third party libraries. The application references or modifies code strictly from sources made available for learning, tutorial and troubleshooting purposes. In particular, this application references code from lynda.com, devtut.wordpress.com, developer.android.com, and stackoverflow.com. External sources of code are annotated via comment with their source URL and date cited.

How to Use the App
------------------
Due to the minimalistic nature of the application, some required functions may be non-obvious at first. Below is a list of actions needed to perform the required functionality. 
- To add a todo item, open the application and hit new in the bottom right corner. The todo is saved persistently when the user taps save after entering a title for the new todo.
- To check or un-check a todo item, tap on it and the todo will check off, strike out, and turn grey. Note that if selection mode is enabled (by tapping select in the bottom left corner) the todo will instead turn orange. To exit selection mode, tap cancel in the bottom right corner.
- To archive a todo or a selection of todos, tap select in the bottom left corner to enter selection mode. Once in selection mode, tap the todos you would like to archive (they will turn orange). From here you can tap the archive button at the bottom of the screen. They will disappear and are then viewable by tapping the menu button in the top right corner and then tapping archive.
- To unarchive a todo, first switch to the archive view by tapping the menu button in the top right corner and then tapping archive. From here, enter selection mode by tapping select in the bottom left corner. Then tap the todos you would like to unarchive and tap unarchive at the bottom of the screen. They will disappear and are viewable by tapping the menu button in the top right orner and then tapping inbox.
- To email a todo, tap select in the bottom left corner, tap the todos you would like to email then tap email at the bottom of the screen. This can be done from within inbox (for non-archived todos) or archive. Remember, you can switch between archive and inbox using the menu button at the top right of the screen.
- To email all todos, including archived. Tap email all todos in from the menu options by tapping the menu button in the top right of the screen.
- To remove a todo, first find the todo you would like to remove by tapping the menu button in the top right corner and choosing either inbox (for non-archived todos) or archive. Then tap select in the bottom left corner, choose the todo you would like to delete (it should turn orange) and finally tap delete on the bottom of the screen.

Known Issues
------------
- Changing views between archive and inbox while in select mode does not turn off select mode, select mode bar persists but does nothing. User must click cancel then re-enable select mode to use select mode.
