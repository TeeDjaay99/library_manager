CREATE DATABASE library_db;

USE library_db;


CREATE TABLE books (
	book_id INT PRIMARY KEY AUTO_INCREMENT,
    book_title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_year INT,
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE borrowers (
	borrower_id INT PRIMARY KEY AUTO_INCREMENT,
    borrower_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255)
);

CREATE TABLE loans (
	loan_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    borrower_id INT NOT NULL,
    loan_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (borrower_id) REFERENCES borrowers(borrower_id) ON DELETE CASCADE
);

INSERT INTO books (book_title, author, published_year, available)
	VALUES ('The Complete Do-It-Yourself Manual', 'Editors of Reader’s Digest', 1973, FALSE),
    ('Cooking 101', 'Martha Stewart', 2010, TRUE),
    ('The Gallic Wars (Commentarii de Bello Gallico)', 'Julius Caesar', 1984, TRUE),
    ('Cleopatra: A Life', 'Stacy Schiff', 2010, TRUE),
    ('The Dictator’s Handbook: Why Bad Behavior is Almost Always Good Politics', 'Alastair Smith', 2011, FALSE);

SELECT * FROM books;

INSERT INTO borrowers (borrower_name, email)
	VALUES ('Bob The Builder', 'bob@example.com'),
    ('Walter White', 'Heisenberg@example.com'),
    ('Julius Caesar', 'Emperor@example.com'),
    ('Cleopatra', 'Queen@example.com'),
    ('Nayib Bukele', 'NB@example.com');
    
SELECT * FROM borrowers;

INSERT INTO loans (book_id, borrower_id, loan_date, return_date)
	VALUES (1, 1, '2024-03-01', NULL),
    (2, 2, '2024-02-20', '2024-03-05'),
    (3, 3, '2024-01-15', '2024-02-01'),
    (4, 4, '2024-02-10', '2024-02-25'),
    (5, 5, '2024-03-05', NULL);
    
SELECT * FROM loans;
    
SELECT 
	loans.loan_id, 
	books.book_title AS book_title, 
	borrowers.borrower_name AS borrower_name, 
	loans.loan_date,
	loans.return_date
FROM loans 
JOIN books ON loans.book_id = books.book_id
JOIN borrowers ON loans.borrower_id = borrowers.borrower_id;