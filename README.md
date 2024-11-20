# School Canteen Ordering System Based on Java and MySQL

## Project Structure:

```
code
│
└── lib
    ├── hutool-all-5.8.10.jar
    └── mysql-connector-j-8.0.31.jar
├── out/production/untitled
    ├── service
        ├── GoodsService.class
        ├── OrderService.class
        ├── RestService.class
        └── StuService.class
    └── view
        ├── LoginFrame.class
        ├── LoginFrame$1.class
        ├── LoginFrame$2.class
        └── MainFrame.class
└── src
    ├── service
        ├── GoodsService.java
        ├── OrderService.java
        ├── RestService.java
        └── StuService.java
    └── view
        ├── AddGoodsFrame.java
        ├── LoginFrame.java
        ├── MainFrame.java
        └── UpdateGoodesFrame.java
```

Both `src` and `out/production/untitled` contain two packages: `service` and `view`.

## Features:

- Student Recharge and Meal Ordering
- Canteen Manager Inventory Management

## Usage:

### 1. MySQL Configuration

Run `./data/db_order.sql` or import `./data/db_order.sql` into MySQL Workbench to create the required database and tables.

### 2. JDK Configuration

Configure the Java Development Kit (JDK) on your system.

### 3. Running the Application

The `.java` files are located in `code/src/`. After compiling, the compiled `.class` files will be generated in `code/out/production/untitled`. The application can then be run.

If you are using Visual Studio Code, you can simply run the project directly.

