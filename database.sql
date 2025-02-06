CREATE TABLE Users
( 
    UserID INTEGER PRIMARY KEY AUTOINCREMENT, 
    Username TEXT NOT NULL, 
    Email TEXT NOT NULL, 
    Password TEXT NOT NULL, 
    Balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00, 
    Role TEXT DEFAULT 'user'
);

CREATE TABLE Books 
( 
    BookID INTEGER PRIMARY KEY AUTOINCREMENT, 
    BookTitle TEXT NOT NULL, 
    BookAuthor TEXT NOT NULL, 
    BookPrice DECIMAL(10, 2) NOT NULL, 
    Quantity INTEGER NOT NULL
);

CREATE TABLE Orders 
( 
    OrderID INTEGER PRIMARY KEY AUTOINCREMENT, 
    UserID INTEGER NOT NULL, 
    OrderDate TEXT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE OrderItems 
( 
    OrderItemID INTEGER PRIMARY KEY AUTOINCREMENT, 
    OrderID INTEGER NOT NULL, 
    ItemType TEXT NOT NULL, 
    ItemID INTEGER NOT NULL, 
    Quantity INTEGER NOT NULL, 
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
);

CREATE TABLE Accessories (
    AccessoryID INTEGER PRIMARY KEY AUTOINCREMENT,
    AccessoryName TEXT NOT NULL,
    AccessoryPrice DECIMAL(10, 2) NOT NULL,
    Quantity INTEGER
);

INSERT INTO Accessories (AccessoryName, AccessoryPrice, Quantity) VALUES 
('Reading Light', 12.99, 34),
('Book Stand', 25.49, 20),
('Leather Bookmark', 5.99, 100),
('Book Organizer Shelf', 79.99, 10),
('Book Cover Protector', 9.49, 80),
('Magnifying Glass for Reading', 14.99, 25),
('E-Reader Cover', 19.99, 40),
('Pen Holder with Book Design', 29.99, 15),
('Notebook with Inspirational Quotes', 10.99, 60),
('Library-Themed Tote Bag', 18.99, 50);

INSERT INTO Books (BookTitle, BookAuthor, BookPrice, Quantity) VALUES 
('The Catcher in the Rye', 'J.D. Salinger', 19.99, 9),
('To Kill a Mockingbird', 'Harper Lee', 14.99, 22),
('1984', 'George Orwell', 12.49, 30),
('Pride and Prejudice', 'Jane Austen', 9.99, 40),
('Moby Dick', 'Herman Melville', 22.99, 10),
('The Great Gatsby', 'F. Scott Fitzgerald', 17.49, 18),
('War and Peace', 'Leo Tolstoy', 39.99, 5),
('Crime and Punishment', 'Fyodor Dostoevsky', 29.99, 8),
('The Hobbit', 'J.R.R. Tolkien', 25.99, 12),
('Brave New World', 'Aldous Huxley', 16.99, 14),
('Fahrenheit 451', 'Ray Bradbury', 13.99, 20),
('Jane Eyre', 'Charlotte Bronte', 14.49, 22),
('Wuthering Heights', 'Emily Bronte', 15.99, 16),
('The Brothers Karamazov', 'Fyodor Dostoevsky', 34.99, 7),
('Les Miserables', 'Victor Hugo', 49.99, 6),
('The Odyssey', 'Homer', 19.99, 11),
('A Tale of Two Cities', 'Charles Dickens', 12.99, 18),
('Sense and Sensibility', 'Jane Austen', 10.49, 24),
('Dracula', 'Bram Stoker', 11.99, 19),
('The Picture of Dorian Gray', 'Oscar Wilde', 14.99, 13);

INSERT INTO Users (Username, Email, Password, Balance, Role) VALUES 
('admin', 'admin@gmail.com', 'admin', 965.02, 'admin'),
('admin1', 'admin1@gmail.com', 'admin1', 1000, 'admin'),
('admin2', 'admin2@gmail.com', 'admin2', 1000, 'admin'),
('admin3', 'admin3@gmail.com', 'admin3', 1000, 'admin'),
('tester', 'tester@gmail.com', 'tester', 100, 'user'),
('dev', 'dev@gmail.com', 'dev', 10000, 'user'),
('manager', 'manager@gmail.com', 'manager', 100000, 'user'),
('user', 'user@gmail.com', 'user', 100, 'user');