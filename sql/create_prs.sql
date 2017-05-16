-- create and select the database
DROP DATABASE IF EXISTS prs;
CREATE DATABASE prs;
USE prs;

-- create the vendors table
CREATE TABLE vendors (
  ID			INT				PRIMARY KEY  AUTO_INCREMENT,
  Code			VARCHAR(10)		NOT NULL,
  Name			VARCHAR(40)		NOT NULL,
  Address		VARCHAR(120)	NOT NULL,
  City			VARCHAR(20)		NOT NULL,
  State			VARCHAR(2)		NOT NULL,
  Zip			VARCHAR(5)		NOT NULL,
  Phone			VARCHAR(12)		NOT NULL,
  EMail			VARCHAR(40)		NOT NULL,
  PreApproved	BIT(1)			NOT NULL,
  CONSTRAINT vcode unique (Code)
);

-- create the products table
CREATE TABLE products (
  ID			INT				PRIMARY KEY  AUTO_INCREMENT,
  Name			VARCHAR(150)	NOT NULL,
  PartNumber	VARCHAR(40)		NOT NULL,
  Price			DECIMAL(10,2)	NOT NULL,
  Unit			VARCHAR(10)		NOT NULL,
  VendorID		INT				NOT NULL,
  PhotoPath		VARCHAR(255)	NOT NULL,
  CONSTRAINT ven_part unique (VendorID, PartNumber),
  Foreign Key (VendorID) references vendors (ID)
);

-- create the users table
CREATE TABLE users (
  ID			INT				PRIMARY KEY  AUTO_INCREMENT,
  UserName		VARCHAR(20)		NOT NULL,
  Password		VARCHAR(20)		NOT NULL,
  FirstName		VARCHAR(20)		NOT NULL,
  LastName		VARCHAR(20)		NOT NULL,
  Phone			VARCHAR(12)		NOT NULL,
  EMail			VARCHAR(75)		NOT NULL,
  Manager		BIT(1)			NOT NULL,
  CONSTRAINT uname unique (UserName)
);

-- create the requests table
CREATE TABLE requests (
  ID			INT				PRIMARY KEY  AUTO_INCREMENT,
  Description	VARCHAR(100)	NOT NULL,
  Justification	VARCHAR(255)	NOT NULL,
  DateNeeded	DATE			NOT NULL,
  UserID		INT				NOT NULL,
  DeliveryMode	VARCHAR(20)		NOT NULL,
  DocsAttached	BIT(1)			NOT NULL,
  Status		VARCHAR(10)		NOT NULL,
  Total			DECIMAL(10,2)	NOT NULL,
  SubmittedDate	DATE			NOT NULL,
  Foreign Key (UserID) references users (ID)
);

-- create the lineitems table
CREATE TABLE lineitems (
	ID			INT				PRIMARY KEY  AUTO_INCREMENT,
	RequestID	INT				NOT NULL,
	ProductID	INT				NOT NULL,
	Quantity	INT				NOT NULL,
	Foreign Key (RequestID) references requests (ID),
	Foreign Key (ProductID) references products (ID),
    CONSTRAINT req_pdt unique (RequestID, ProductID)
);

-- create a user and grant privileges to that user
GRANT SELECT, INSERT, DELETE, UPDATE
ON prs.*
TO prs_user@localhost
IDENTIFIED BY 'sesame';
