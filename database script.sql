CREATE TABLE Lot (
  lotName  VARCHAR(20) PRIMARY KEY,
  location VARCHAR(20),
  capacity INT,
  floors   INT
);

INSERT Lot
VALUES ('North Lot', 'UWT', 200, 3),
       ('South Lot', 'UWT', 200, 3),
       ('East Lot', 'UWT', 200, 3),
       ('West Lot', 'UWT', 200, 3);

CREATE TABLE Space (
  spaceNumber INT PRIMARY KEY,
  spaceType   VARCHAR(20),
  lotName     VARCHAR(20),
  FOREIGN KEY (lotName) REFERENCES Lot (lotName)
);

INSERT Space
VALUES (1, 'uncover', 'North Lot'),
       (2, 'uncover', 'North Lot'),
       (3, 'cover', 'North Lot'),
       (4, 'cover', 'North Lot');

CREATE TABLE CoveredSpace (
  spaceNumber INT PRIMARY KEY,
  monthlyRate FLOAT,
  FOREIGN KEY (spaceNumber) REFERENCES Space (spaceNumber)
);

INSERT CoveredSpace
VALUES (3, 100.0),
       (4, 100.0);

CREATE TABLE UncoveredSpace (
  spaceNumber INT PRIMARY KEY,
  FOREIGN KEY (spaceNumber) REFERENCES Space (spaceNumber)
);

INSERT UncoveredSpace
VALUES (1),
       (2);


CREATE TABLE Staff (
  staffNumber          INT PRIMARY KEY,
  telephoneExt         VARCHAR(20),
  vehicleLicenseNumber VARCHAR(20)
);

INSERT Staff
VALUES (000, '123', 'ABC123'),
       (001, '980', 'CCC113'),
       (098, '129', 'BBI444');

CREATE TABLE StaffSpace (
  staffNumber INT,
  spaceNumber INT,
  PRIMARY KEY (staffNumber, spaceNumber),
  FOREIGN KEY (staffNumber) REFERENCES Staff (staffNumber),
  FOREIGN KEY (spaceNumber) REFERENCES CoveredSpace (spaceNumber)
);

INSERT StaffSpace
VALUES (000, 3),
       (001, 4);

CREATE TABLE SpaceBooking (
  bookingID      SERIAL PRIMARY KEY,
  spaceNumber    INT,
  staffNumber    INT,
  visitorLicense VARCHAR(20),
  dateOfVisit    DATETIME,
  FOREIGN KEY (spaceNumber) REFERENCES CoveredSpace (spaceNumber),
  FOREIGN KEY (staffNumber) REFERENCES Staff (staffNumber)
);

