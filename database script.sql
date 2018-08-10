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
VALUES (1, 'Uncovered', 'North Lot'),
       (2, 'Uncovered', 'West Lot'),
       (3, 'Covered', 'South Lot'),
       (4, 'Covered', 'East Lot'),
       (5, 'Uncovered', 'West Lot'),
       (6, 'Uncovered', 'North Lot'),
       (7, 'Covered', 'South Lot'),
       (8, 'Covered', 'East Lot'),
       (9, 'Uncovered', 'North Lot'),
       (10, 'Uncovered', 'West Lot'),
       (11, 'Covered', 'South Lot'),
       (12, 'Covered', 'East Lot'),
       (13, 'Uncovered', 'West Lot'),
       (14, 'Uncovered', 'North Lot'),
       (15, 'Covered', 'South Lot'),
       (16, 'Covered', 'East Lot');

CREATE TABLE CoveredSpace (
  spaceNumber INT PRIMARY KEY,
  monthlyRate FLOAT,
  FOREIGN KEY (spaceNumber) REFERENCES Space (spaceNumber)
);

INSERT CoveredSpace
VALUES (3, 100.0),
       (4, 100.0),
       (5, 23),
       (7, 12),
       (8, 65),
       (11, 32),
       (12, 53),
       (15, 45),
       (16, 100);

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

INSERT SpaceBooking
VALUES (NULL, 11, 1, 'PL473', '2018-09-10'),
       (NULL, 8, 1, 'V3H1CL3', '2018-09-12'),
       (NULL, 12, 0, '4U70', '2018-09-10');


