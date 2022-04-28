create database fpt;
use fpt;

CREATE TABLE users(
    UserId int NOT NULL AUTO_INCREMENT,
    FirstName varchar(255) NOT NULL,
    UserName varchar(255),
    Password varchar(255),
    Role varchar(45),
    PRIMARY KEY (UserId)
);

CREATE TABLE certifications(
    CertificationId int NOT NULL AUTO_INCREMENT,
    CertificationName varchar(255) NOT NULL,
    CertificationAbbreviation varchar(255) NOT NULL,
    TreatmentName varchar(255) NOT NULL,
    PRIMARY KEY (CertificationId)
);

CREATE TABLE usercertifications(
    UserCertificationId int NOT NULL AUTO_INCREMENT,
    UserId int NOT NULL,
    CertificationId int NOT NULL,
    PRIMARY KEY (UserCertificationId),
    FOREIGN KEY (UserId) REFERENCES users(UserId),
    FOREIGN KEY (CertificationId) REFERENCES certifications(CertificationId)
);

CREATE TABLE appointments(
    AppointmentId int NOT NULL AUTO_INCREMENT,
    UserId int NOT NULL,
    AppointmentDate varchar(255) NOT NULL,
    TreatmentName varchar(255),
    CustomerName varchar(255),
    AppointmentNotes MEDIUMTEXT,
    PRIMARY KEY (AppointmentId),
    FOREIGN KEY (UserId) REFERENCES users(UserId)
);

CREATE TABLE defaultschedules (
	`ScheduleId` INT NOT NULL AUTO_INCREMENT,
    `UserId` INT NOT NULL,
    `DayOfWeek` VARCHAR(45) NULL,
	`StartHour` INT NULL,
	`EndHour` INT NULL,
  PRIMARY KEY (`ScheduleId`),
  FOREIGN KEY (UserId) REFERENCES users(UserID)
);

INSERT INTO
	certifications (CertificationName, CertificationAbbreviation, TreatmentName)
VALUES
	('Advanced Exercise Therapist','AET','Exercise Therapy'),
	('Certificate of Achievement in Pregnancy and Postpartum Physical Therapy','CAPP-OB','Pregnancy/Postpartum Therapy'),
	('Certificate of Achievement in Pelvic Physical Therapy','CAPP-Pelvic','Pelvic Therapy'),
	('Certified Hand Therapist','CHT','Hand Therapy'),
	('Certified Kinesio Taping Practitioner','CKTP','Taping Therapy'),
	('Certified Lymphedema Therapist','CLT','Lymphedema Therapy'),
	('Certification in Rehabilitative Exercise','CREX','Rehabilitative Therapy'),
	('Certified Strength and Conditioning Specialist','CSCS','Conditioning Therapy'),
	('Certified Yoga Therapist','CYT','Yoga Therapy'),
	('Geriatric Certified Specialist','GCS','Geriatic Therapy'),
	('Licensed Massage Therapist','LMT','Massage Therapy'),
	('Orthopedic Certified Specialist','OCS','Orthopedic Therapy'),
	('Postural Alignment Specialist','PAS','Alignment Therapy'),
	('Pediatric Certified Specialist','PCS','Pediatric Therapy'),
	('Sports Certified Specialist','SCS','Sports Therapy'),
	('Women’s Certified Specialist','WCS','Womens Therapy');

INSERT INTO
	users (FirstName, UserName, Password, Role)
VALUES	
	('Abraham','Abraham','123','therapist'),
	('Aeshia','Aeshia','123','therapist'),
	('April','April','123','therapist'),
	('Art','Art','123','therapist'),
	('Becky','Becky','123','therapist'),
	('Carmen','Carmen','123','therapist'),
	('Cole','Cole','123','therapist'),
	('Dennis','Dennis','123','therapist'),
	('Jade','Jade','123','therapist'),
	('June','June','123','therapist'),
	('Kennan','Kennan','123','therapist'),
	('Larry','Kennan','123','therapist'),
	('Latesha','Latesha','123','therapist'),
	('Mary','Mary','123','therapist'),
	('Mario','Mario','123','therapist'),
	('Nathan','Nathan','123','therapist'),
	('Orlando','Orlando','123','therapist'),
	('Rick','Rick','123','therapist'),
	('Sarah','Sarah','123','therapist'),
	('Sasha','Sasha','123','therapist'),
	('Wilbur','Wilbur','123','therapist'),
    ('Admin','Admin','123','admin');

insert into
	usercertifications (UserId,CertificationId)
Value
	(1,6),(1,11),
    (2,1),(2,4),(2,7),(2,8),(2,9),(2,11),(2,12),(2,15),(2,16),
    (3,2),(3,5),(3,16),
    (4,3),(4,4),(4,5),(4,12),(4,13),
    (5,2),(5,10),
    (6,1),
    (7,1),(7,4),(7,7),(7,8),(7,9),(7,11),(7,15),
    (8,2),(8,13),(8,16),
    (9,5),(9,6),(9,11),
    (10,9),(10,14),(10,16),
    (11,7),(11,8),
    (12,14),
    (13,8),(13,15),
    (14,9),
    (15,1),(15,5),(15,15),
    (16,4),(16,12),
    (17,10),
    (18,2),(18,3),(18,9),(18,11),(18,16),
    (19,1),(19,5),(19,8),(19,15),
    (20,3),(20,6),(20,7),(20,8),(20,10),(20,13),(20,16),
    (21,2),(21,3),(21,16);
    
insert into
	defaultschedules (UserId, DayOfWeek, StartHour, EndHour)
VALUES
(1,"MONDAY",8,12),
(1,"TUESDAY",12,16),
(1,"WEDNESDAY",8,12),
(1,"THURSDAY",12,16),
(1,"FRIDAY",8,12),
(1,"SATURDAY",12,16),

(2,"MONDAY",8,17),
(2,"TUESDAY",8,17),
(2,"WEDNESDAY",null,null),
(2,"THURSDAY",8,17),
(2,"FRIDAY",8,17),
(2,"SATURDAY",8,17),

(3,"MONDAY",12,16),
(3,"TUESDAY",8,12),
(3,"WEDNESDAY",12,16),
(3,"THURSDAY",8,12),
(3,"FRIDAY",12,16),
(3,"SATURDAY",8,12),

(4,"MONDAY",null,null),
(4,"TUESDAY",8,17),
(4,"WEDNESDAY",8,17),
(4,"THURSDAY",8,17),
(4,"FRIDAY",8,17),
(4,"SATURDAY",8,17),

(5,"MONDAY",12,16),
(5,"TUESDAY",8,12),
(5,"WEDNESDAY",12,16),
(5,"THURSDAY",8,12),
(5,"FRIDAY",12,16),
(5,"SATURDAY",8,12),

(6,"MONDAY",8,12),
(6,"TUESDAY",12,16),
(6,"WEDNESDAY",8,12),
(6,"THURSDAY",12,16),
(6,"FRIDAY",8,12),
(6,"SATURDAY",12,16),

(7,"MONDAY",8,17),
(7,"TUESDAY",null,null),
(7,"WEDNESDAY",8,17),
(7,"THURSDAY",8,17),
(7,"FRIDAY",8,17),
(7,"SATURDAY",8,17),

(8,"MONDAY",8,12),
(8,"TUESDAY",12,16),
(8,"WEDNESDAY",8,12),
(8,"THURSDAY",12,16),
(8,"FRIDAY",8,12),
(8,"SATURDAY",12,16),

(9,"MONDAY",8,12),
(9,"TUESDAY",12,16),
(9,"WEDNESDAY",8,12),
(9,"THURSDAY",12,16),
(9,"FRIDAY",8,12),
(9,"SATURDAY",12,16),

(10,"MONDAY",8,12),
(10,"TUESDAY",12,16),
(10,"WEDNESDAY",8,12),
(10,"THURSDAY",12,16),
(10,"FRIDAY",8,12),
(10,"SATURDAY",12,16),

(11,"MONDAY",12,16),
(11,"TUESDAY",8,12),
(11,"WEDNESDAY",12,16),
(11,"THURSDAY",8,12),
(11,"FRIDAY",12,16),
(11,"SATURDAY",8,12),

(12,"MONDAY",12,16),
(12,"TUESDAY",8,12),
(12,"WEDNESDAY",12,16),
(12,"THURSDAY",8,12),
(12,"FRIDAY",12,16),
(12,"SATURDAY",8,12),

(13,"MONDAY",8,12),
(13,"TUESDAY",12,16),
(13,"WEDNESDAY",8,12),
(13,"THURSDAY",12,16),
(13,"FRIDAY",8,12),
(13,"SATURDAY",12,16),

(14,"MONDAY",12,16),
(14,"TUESDAY",8,12),
(14,"WEDNESDAY",12,16),
(14,"THURSDAY",8,12),
(14,"FRIDAY",12,16),
(14,"SATURDAY",8,12),

(15,"MONDAY",8,12),
(15,"TUESDAY",12,16),
(15,"WEDNESDAY",8,12),
(15,"THURSDAY",12,16),
(15,"FRIDAY",8,12),
(15,"SATURDAY",12,16),

(16,"MONDAY",8,12),
(16,"TUESDAY",12,16),
(16,"WEDNESDAY",8,12),
(16,"THURSDAY",12,16),
(16,"FRIDAY",8,12),
(16,"SATURDAY",12,16),

(17,"MONDAY",12,16),
(17,"TUESDAY",8,12),
(17,"WEDNESDAY",12,16),
(17,"THURSDAY",8,12),
(17,"FRIDAY",12,16),
(17,"SATURDAY",8,12),

(18,"MONDAY",8,17),
(18,"TUESDAY",8,17),
(18,"WEDNESDAY",8,17),
(18,"THURSDAY",null,null),
(18,"FRIDAY",8,17),
(18,"SATURDAY",8,17),

(19,"MONDAY",8,17),
(19,"TUESDAY",8,17),
(19,"WEDNESDAY",8,17),
(19,"THURSDAY",8,17),
(19,"FRIDAY",null,null),
(19,"SATURDAY",8,17),

(20,"MONDAY",null,null),
(20,"TUESDAY",8,17),
(20,"WEDNESDAY",8,17),
(20,"THURSDAY",8,17),
(20,"FRIDAY",8,17),
(20,"SATURDAY",8,17),

(21,"MONDAY",12,16),
(21,"TUESDAY",8,12),
(21,"WEDNESDAY",12,16),
(21,"THURSDAY",8,12),
(21,"FRIDAY",12,16),
(21,"SATURDAY",8,12);