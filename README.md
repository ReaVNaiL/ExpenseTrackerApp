# Budget Tracker App
In today's fast-paced world, managing personal finances has become increasingly important for individuals and families. The ability to track and control one's expenses is vital for financial success and stability. This document discusses the development of a mobile application called **Budget Tracker App** which aims to provide users with an easy and efficient way to track and manage their expenses. 

## Background and Motivation
The idea for the Budget Tracker App emerged from the realization that many people often make purchases at stores, and then either bring receipts home or discard them immediately. This practice makes it challenging to keep track of expenses and can lead to financial disorganization.

>The need for a mobile application that addresses this issue and provides a comprehensive solution for tracking expenses prompted the development of the Budget Tracker App.

## Key Features
* The application offers essential user functionality and is designed to serve as a personal financial assistant. It is particularly useful for those who frequently make purchases at stores and need a convenient way to record and monitor their spending habits.
* One of the most notable features of the Budget Tracker App is its ability to display the content of a receipt directly within the application. 
* Users can easily add expenses or receipts, which are then stored in a transaction history and displayed in descending order by month.

## Basic Structure
The overall structure of the Budget Tracker App is designed to provide a user-friendly experience, featuring a straightforward `Main Activity` with three primary options: Dashboard, Settings, and Exit. These options are easily accessible, allowing users to navigate effortlessly between them at any time.

* **Dashboard**: This serves as the central hub for managing receipts and monitoring expenses.
* **Settings**: Here, users can adjust their budget preferences and customize various aspects of the app to suit their needs.

### Dashboard Activity
The focus here was on three key areas: color scheme, layout, and data visualization.
* **Color Scheme**: A consistent blue tonality was chosen to enhance readability.
* **Layout**: The Dashboard follows a clean and intuitive layout.
* **Data Visualization**: Graphs were incorporated using a plugin/library discovered on the Android Studio forums, developed by PhilJay.

### Settings Activity
An integral component of the Budget Tracker App is a dedicated section for updating budgets, as the app would be incomplete without this essential functionality. This section encompasses an additional activity, which, although relatively simple, plays a critical role in allowing users to effectively manage their budgets.

## How to Contribute
We welcome contributions to the Budget Tracker App project. Please feel free to fork this repository, make changes in your local branch, and then submit a pull request.

## ToDo List
* Implement a feature to capture and save a picture of the receipt
* Add feature for exporting transaction history in CSV format
* Include a section for tracking income, for a more comprehensive financial overview
* Incorporate push notifications to remind users to update their expenses

## Specs
* Platform: Android
* Language: Java
* Database: SQLite
* IDE: Android Studio