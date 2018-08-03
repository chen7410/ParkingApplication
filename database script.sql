CREATE TABLE Lot (
  lotName VARCHAR(20) PRIMARY KEY,
  location VARCHAR(20),
  capacity INT,
  floors INT
);

CREATE TABLE Space (
  spaceNumber INT PRIMARY KEY,
  spaceType VARCHAR(20),
  lotName VARCHAR(20) REFERENCES Lot(lotName)
);

CREATE TABLE CoveredSpace (
  spaceNumber INT PRIMARY KEY REFERENCES Space(spaceNumber),
  monthlyRate FLOAT
);

CREATE TABLE UncoveredSpace (
  spaceNumber INT PRIMARY KEY REFERENCES Space(spaceNumber)
);

CREATE TABLE StaffSpace (
  staffNumber INT REFERENCES Staff(staffNumber),
  spaceNumber INT REFERENCES CoveredSpace(spaceNumber),
  PRIMARY KEY (staffNumber, spaceNumber)
);

CREATE TABLE Staff (
  staffNumber INT PRIMARY KEY,
  telephoneExt INT,
  vehicleLicenseNumber VARCHAR(20)
);

CREATE TABLE SpaceBooking (
  bookingID INT PRIMARY KEY,
  spaceNumber INT REFERENCES CoveredSpace(spaceNumber),
  staffNumber INT REFERENCES Staff(staffNumber),
  visitorLicense VARCHAR(20),
  dateOfVisit DATETIME
);