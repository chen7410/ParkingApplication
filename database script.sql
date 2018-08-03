CREATE TABLE LOT (
  lotName VARCHAR(20) PRIMARY KEY,
  location VARCHAR(20),
  capacity INT,
  floors INT
);

CREATE TABLE SPACE (
  spaceNumber INT PRIMARY KEY,
  spaceType VARCHAR(20),
  lotName VARCHAR(20) REFERENCES LOT(lotName)
);

CREATE TABLE COVERED_SPACE (
  spaceNumber INT PRIMARY KEY REFERENCES SPACE(spaceNumber),
  monthlyRate FLOAT
);

CREATE TABLE UNCOVERED_SPACE (
  spaceNumber INT PRIMARY KEY REFERENCES SPACE(spaceNumber)
);

CREATE TABLE STAFF_SPACE (
  staffNumber INT REFERENCES STAFF(staffNumber),
  spaceNumber INT REFERENCES COVERED_SPACE(spaceNumber),
  PRIMARY KEY (staffNumber, spaceNumber)
);

CREATE TABLE STAFF (
  staffNumber INT PRIMARY KEY,
  telephoneExt INT,
  vehicleLicenseNumber VARCHAR(20)
);

CREATE TABLE SPACE_BOOKING (
  bookingID INT PRIMARY KEY,
  spaceNumber INT REFERENCES COVERED_SPACE(spaceNumber),
  staffNumber INT REFERENCES STAFF(staffNumber),
  visitorLicense VARCHAR(20),
  dateOfVisit DATETIME
);