# CS360
SNHU CS-360 Course Repo

### Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

The goal of this project was to create a functioning Inventory application that allows the user to maintain a personal database of items. These items include a name, description, and stock count. In order to facilitate this application, I created a user interface that performs CRUD actions using a SQL database to manage the user's inventory and items.

### What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?

To facilitate creating items, viewing items, deleting, and updating items, and other features, I created the following screens:

- Login Screen that checks the user's credentials against a user database
- Main recycler view populated with all of the item entries in the Inventory database, showing the item name, description, count, and Delete Button for each item
- Add item view that allows the user to add items to their database
- Detail view that displayed all of the item's information in a readable layout
- Edit view that allows the users to edit the item's information and save changes
- A delete confirmation view that shows the item's information and asks the user to make sure they want to delete the information before it is purged from the database
- SMS Notification view that tells the user why SMS_Read permissions are requested and allows the user to receive SMS notifications for low-stock items

My designs were successful because they were consistent and used common design approaches that are easy for the user to use and navigate with. Regardless of where the user is in the app, it's always simple to get back to the main view with the click of a button and does not require the user to reach far for or click any small back buttons.

### How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

With Project Two, I created all of the user interface first, which helped plan for and organize the application's core functions. For coding functionality, I went screen by screen and implemented the functionality needed for each activity. I then branched to related activities to pass information and implement their functionality as well. I've learned from these projects how helpful it is to have the user interface completed before coding functionality, and I will build applications accordingly in the future.

### How did you test to ensure your code was functional? Why is this process important and what did it reveal?

As I was developing my code, I tested constantly to make sure that my buttons and interface items worked consistently and how I expected them to perform. Regardless, I had to do quite a bit of fixing as I went, which helped ensure that my final product worked as expected. Significant errors are very easy to create, like an instance where I accidentally had stock counts resolve a byte string instead of a normal string when they were updated. The item would start normally, but then when it was updated, the count would subtract by 256. If an item was supposed to show a count of 10, it would update to -246. It was an interesting problem to solve, but testing thoroughly helps catch oddities.

### Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

Coding functionality was definitely the most difficult part of the process, simply because there are so many built-in functions that are needed, and I therefore had to learn how to use them. I had trouble figuring out how some needed functions differ depending on whether someone is working in an activity or a Java class. I had to do a bit of research to figure out how to perform certain functions where I wanted to perform them.

### In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

I am particularly proud of how I was able to pass intent information between activities so that the edit view was able to populate the update fields with current information. It took me a while to figure out how to do this, but I eventually solved it, and I believe the application is better for it.
