CREATE TABLE Customer (
     customerID INTEGER IDENTITY NOT NULL
        CONSTRAINT Customer_PK PRIMARY KEY,
     firstName VARCHAR(100) NOT NULL,
     secondName VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     phone VARCHAR(12) NOT NULL
);

CREATE TABLE Employee (
     employeeID INTEGER IDENTITY NOT NULL
         CONSTRAINT Employee_PK PRIMARY KEY,
     firstName VARCHAR(100) NOT NULL,
     lastName VARCHAR(100) NOT NULL,
     address VARCHAR(200) NOT NULL,
     phone VARCHAR(12) NOT NULL,
     birthDate DATE NOT NULL,
     start DATETIME NOT NULL,
     "end" DATETIME,
     position VARCHAR(50) NOT NULL,
     password VARCHAR(500) NOT NULL
);

CREATE TABLE Payment (
     paymentID INTEGER IDENTITY NOT NULL
         CONSTRAINT Payment_PK PRIMARY KEY,
     type VARCHAR(100) NOT NULL,
     total BIGINT NOT NULL,
     successful INTEGER NOT NULL,
     created DATETIME NOT NULL,
     employeeID INTEGER
);

CREATE TABLE Product (
     productID INTEGER IDENTITY NOT NULL
        CONSTRAINT Product_PK PRIMARY KEY,
     type INTEGER NOT NULL,
     name VARCHAR(200) NOT NULL,
     count INTEGER NOT NULL,
     price INTEGER NOT NULL,
     lastCheck DATETIME
);

CREATE TABLE "Table" (
     tableID INTEGER IDENTITY NOT NULL
         CONSTRAINT Table_PK PRIMARY KEY,
     reserved INTEGER,
     occupied INTEGER
);

CREATE TABLE Reservation (
     reservationID INTEGER IDENTITY NOT NULL
         CONSTRAINT Reservation_PK PRIMARY KEY,
     start DATETIME NOT NULL,
     "end" DATETIME,
     peopleCount INTEGER,
     created DATETIME NOT NULL,
     canceled INTEGER,
     description VARCHAR(500),
     customerID INTEGER,
     tableID INTEGER
);

CREATE TABLE "Order" (
     orderID INTEGER IDENTITY NOT NULL
        CONSTRAINT Order_PK PRIMARY KEY,
     created DATETIME NOT NULL,
     employeeID INTEGER,
     paymentID INTEGER NOT NULL
);

CREATE TABLE OrderProducts (
     count INTEGER NOT NULL,
     productID INTEGER,
     orderID NUMERIC (28),
     CONSTRAINT OrderProducts_PK PRIMARY KEY (productID, orderID)
);

CREATE TABLE TableOrders (
     tableID INTEGER NOT NULL,
     orderID INTEGER NOT NULL,
     CONSTRAINT TableOrders_PK PRIMARY KEY (tableID, orderID)
);

ALTER TABLE "Order" ADD CONSTRAINT Order_Employee_FK FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
ALTER TABLE "Order" ADD CONSTRAINT Order_Payment_FK FOREIGN KEY (paymentID) REFERENCES Payment (paymentID);
ALTER TABLE OrderProducts ADD CONSTRAINT OrderProducts_Product_FK FOREIGN KEY (productID) REFERENCES Product (productID);
ALTER TABLE Payment ADD CONSTRAINT Payment_Employee_FK FOREIGN KEY (employeeID) REFERENCES Employee (employeeID);
ALTER TABLE Reservation ADD CONSTRAINT Reservation_Customer_FK FOREIGN KEY (customerID) REFERENCES Customer (customerID);
ALTER TABLE Reservation ADD CONSTRAINT Reservation_Table_FK FOREIGN KEY (tableID) REFERENCES "Table" (tableID) ON DELETE cascade ON UPDATE NO ACTION;
ALTER TABLE TableOrders ADD CONSTRAINT TableOrders_Order_FK FOREIGN KEY (orderID) REFERENCES "Order" (orderID) ON DELETE cascade ON UPDATE NO ACTION;
ALTER TABLE TableOrders ADD CONSTRAINT TableOrders_Table_FK FOREIGN KEY (tableID) REFERENCES "Table" (tableID) ON DELETE cascade ON UPDATE NO ACTION;