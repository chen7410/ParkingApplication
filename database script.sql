
CREATE TABLE Lot (
  lotName VARCHAR(20) PRIMARY KEY,
  location VARCHAR(20),
  capacity INT,
  floors INT
);

INSERT Lot VALUES
('North Lot', 'UWT', 200, 3),
('South Lot', 'UWT', 200, 3),
('East Lot', 'UWT', 200, 3),
('West Lot', 'UWT', 200, 3);

CREATE TABLE Space (
  spaceNumber INT PRIMARY KEY,
  spaceType VARCHAR(20),
  lotName VARCHAR(20) REFERENCES Lot(lotName)
);

INSERT Space VALUES
(1, 'uncover', 'North Lot'),
(2, 'uncover', 'North Lot'),
(3, 'cover', 'North Lot'),
(4, 'cover', 'North Lot');

CREATE TABLE CoveredSpace (
  spaceNumber INT PRIMARY KEY REFERENCES SPACE(spaceNumber),
  monthlyRate FLOAT
);

INSERT CoveredSpace VALUES
(3, 100.0),
(4, 100.0);

CREATE TABLE UncoveredSpace (
  spaceNumber INT PRIMARY KEY REFERENCES SPACE(spaceNumber)
);

INSERT UncoveredSpace VALUES
(1),
(2);

CREATE TABLE StaffSpace (
  staffNumber INT REFERENCES STAFF(staffNumber),
  spaceNumber INT REFERENCES COVERED_SPACE(spaceNumber),
  PRIMARY KEY (staffNumber, spaceNumber)
);

INSERT StaffSpace VALUES
(000, 3),
(001, 4);

CREATE TABLE Staff (
  staffNumber INT PRIMARY KEY,
  telephoneExt INT,
  vehicleLicenseNumber VARCHAR(20)
);

INSERT Staff VALUES
(000, 123, 'ABC123'),
(001, 980, 'CCC113'),
(098, 129, 'BBI444');


CREATE TABLE SpaceBooking (
  bookingID SERIAL PRIMARY KEY,
  spaceNumber INT REFERENCES CoveredSpace(spaceNumber),
  staffNumber INT REFERENCES Staff(staffNumber),
  visitorLicense VARCHAR(20),
  dateOfVisit DATETIME
);

