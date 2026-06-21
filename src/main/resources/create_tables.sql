-- Create tables for Bookify Travel Agency Database

-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    address TEXT NOT NULL
);

-- Create destinations table
CREATE TABLE IF NOT EXISTS destinations (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    country TEXT NOT NULL,
    description TEXT NOT NULL,
    price_per_person REAL NOT NULL
);

-- Create bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    customer_id INTEGER NOT NULL,
    destination_id INTEGER NOT NULL,
    booking_date TEXT NOT NULL,
    travel_date TEXT NOT NULL,
    number_of_people INTEGER NOT NULL,
    total_price REAL NOT NULL,
    status TEXT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (destination_id) REFERENCES destinations(id)
); 