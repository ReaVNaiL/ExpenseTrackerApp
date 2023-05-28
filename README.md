# Budget Tracker App

<p>
    <img width=335 height=425 src="https://github.com/ReaVNaiL/ExpenseTrackerApp/assets/59776018/1401826a-e9a9-49c0-a5e3-7fc336e9a6da"/>
    <img width=335 height=425 src="https://github.com/ReaVNaiL/ExpenseTrackerApp/assets/59776018/f5703fec-acbe-4a6f-b28b-9abafa09715d"/>
</p>

In today's fast-paced world, managing personal finances has become increasingly important for individuals and families. The ability to track and control one's expenses is vital for financial success and stability. This document discusses the development of a mobile application called **Budget Tracker App** which aims to provide users with an easy and efficient way to track and manage their expenses.

## Background and Motivation

The idea for the Budget Tracker App emerged from the realization that many people often make purchases at stores, and then either bring receipts home or discard them immediately. This practice makes it challenging to keep track of expenses and can lead to financial disorganization. 

> The need for a mobile application that addresses this issue and provides a comprehensive solution for tracking expenses prompted the development of the Budget Tracker App.

## Key Features

- The application offers essential user functionality and is designed to serve as a personal financial assistant. It is particularly useful for those who frequently make purchases at stores and need a convenient way to record and monitor their spending habits.
- One of the most notable features of the Budget Tracker App is its ability to display the content of a receipt directly within the application.
- Users can easily add expenses or receipts, which are then stored in a transaction history and displayed in descending order by month.

<p>
    <img width=565 height=425 src="https://github.com/ReaVNaiL/ExpenseTrackerApp/assets/59776018/3bc8a1d1-9424-4585-a442-cbe3799978a6"/>
</p>

## Basic Structure

The overall structure of the Budget Tracker App is designed to provide a user-friendly experience, featuring a straightforward `Main Activity` with three primary options: Dashboard, Settings, and Exit. These options are easily accessible, allowing users to navigate effortlessly between them at any time.

- **Dashboard**: This serves as the central hub for managing receipts and monitoring expenses.
- **Settings**: Here, users can adjust their budget preferences and customize various aspects of the app to suit their needs.

## Technical Details

- #### MainActivity

This is the entry point of the application. It features three buttons: Dashboard, Settings, and Exit. The Dashboard button leads to the `DashboardViewListActivity`, where users can view and manage their expenses. The Settings button leads to the `SettingsEditActivity` where users can adjust their preferences.

```java
public class MainActivity extends AppCompatActivity {  
  protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_main);  
```

- The Dashboard button is implemented using an `Intent` that starts the `DashboardViewListActivity` when the user clicks the button.

```java
    Button dashboardButton = findViewById(R.id.dashboard_button);  
    dashboardButton.setOnClickListener(v -> {  
      Intent intent = new Intent(MainActivity.this, DashboardViewListActivity.class);  
      startActivity(intent);  
    });  
```

- The Settings button is implemented using an `Intent` that starts the `SettingsEditActivity` when the user clicks the button.
```java
    Button settingsButton = findViewById(R.id.settings_button);  
    settingsButton.setOnClickListener(v -> {  
      Intent intent = new Intent(MainActivity.this, SettingsEditActivity.class);  
      startActivity(intent);  
    });  
```

- The Exit button is implemented using the `finish()` and `System.exit(0)` methods. This ensures that the application is completely closed when the user clicks the Exit button.

```java
    Button exitButton = findViewById(R.id.exit_button);  
    exitButton.setOnClickListener(v -> {  
      finish();  
      System.exit(0);
    });
  }
}
```

- #### DashboardViewListActivity

This activity manages the display of the expenses list. It utilizes a `RecyclerView` for efficient display and scrolling of a large number of expense items. Additionally, a `BarChart` is used to visualize the expenses. The expenses data is fetched from a `DataHandler` object that interacts with the SQLite database.

```java
public class DashboardViewListActivity extends AppCompatActivity {  
  private List<Expense> expenseList;  
  private RecyclerView recyclerView;  
  private BarChart barChart;  
  private DataHandler dbHandler;  

  protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_dashboard_expenses);  

    FloatingActionButton addExpenseButton = findViewById(R.id.add_expense_button);  
    addExpenseButton.setOnClickListener(this::addExpenses);  

    dbHandler = new DataHandler(this);
  }
}
```

- #### DataHandler

This class serves as the primary database interface for the app. The `getAllExpenses()` method is used to fetch all the expenses from the database.

- The `getAllExpenses()` method is implemented using a `Cursor` object that iterates through the database and returns a list of all the expenses.

```java
private Boolean setExpensesList() {  
  try {  
    dbHandler.open();  
    expenseList = dbHandler.getAllExpenses();  
    return true;  
  } catch (Exception e) {  
    e.printStackTrace();  
    return false;
  }
}
```

- #### AddExpenseDialog

This component is invoked when users click the Add Expense button on the Dashboard. It captures the details of the new expense and saves it to the database.

```java
public void addExpenses(View view) {  
  AddExpenseDialog.addExpense(this);
}
```

## Future Enhancements

- Implement a feature to capture and save a picture of the receipt.
- Add a feature for exporting transaction history in CSV format.
- Include a section for tracking income, for a more comprehensive financial overview.
- Incorporate push notifications to remind users to update their expenses.

## Technology Stack

| * | * |
| --- | --- |
| **Platform** | Android |
| **Language** | Java |
| **Database** | SQLite |
| **IDE** | Android Studio |

## How to Contribute

We welcome contributions to the Budget Tracker App project. Please feel free to fork this repository, make changes in your local branch, and then submit a pull request.