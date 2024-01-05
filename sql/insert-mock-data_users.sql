-- mock data

INSERT INTO users (first_name, last_name, birth_date, gender, email, password, is_doctor)
VALUES 
  ('Harry', 'Potter', '1980-07-31', 'M', 'harry.potter@email.com', 'password123', true),
  ('Hermione', 'Granger', '1979-09-19', 'F', 'hermione.granger@email.com', 'password456', true),
  ('Ron', 'Weasley', '1980-03-01', 'M', 'ron.weasley@email.com', 'password789', true),
  ('Jon', 'Snow', '1986-12-08', 'M', 'jon.snow@email.com', 'passwordabc', true),
  ('Daenerys', 'Targaryen', '1986-10-23', 'F', 'daenerys.targaryen@email.com', 'passworddef', true),
  ('Tyrion', 'Lannister', '1980-11-27', 'M', 'tyrion.lannister@email.com', 'passwordghi', false),
  ('Luke', 'Skywalker', '1971-05-04', 'M', 'luke.skywalker@email.com', 'passwordjkl', false),
  ('Leia', 'Organa', '1977-04-22', 'F', 'leia.organa@email.com', 'passwordmno', false),
  ('Han', 'Solo', '1973-01-29', 'M', 'han.solo@email.com', 'passwordpqr', false),
  ('Arya', 'Stark', '2001-11-25', 'F', 'arya.stark@email.com', 'passwordstu', false),
  ('Sansa', 'Stark', '1999-02-05', 'F', 'sansa.stark@email.com', 'passwordvwx', false),
  ('Darth', 'Vader', '1977-09-13', 'M', 'darth.vader@email.com', 'passwordyz', false),
  ('Obi-Wan', 'Kenobi', '1972-08-19', 'M', 'obiwan.kenobi@email.com', 'password123', false),
  ('Padmé', 'Amidala', '1974-04-21', 'F', 'padme.amidala@email.com', 'password456', false),
  ('Margaery', 'Tyrell', '1988-06-15', 'F', 'margaery.tyrell@email.com', 'password789', false);


-- mock data with hashed password

INSERT INTO users (first_name, last_name, birth_date, gender, email, password, is_doctor)
VALUES 
  ('Harry', 'Potter', '1980-07-31', 'M', 'harry.potter@email.com', '$2a$10$tx5oEDbObThQJTr7.fGhy.gqvLT5OI6OEKe716aRFX9EvJcLdV4W6', true),
  ('Hermione', 'Granger', '1979-09-19', 'F', 'hermione.granger@email.com', '$2a$10$Di2kZNLPbkVW530mxwyxSufDe61kwzUEeF194m8GE4BjsioNU7RPW', true),
  ('Ron', 'Weasley', '1980-03-01', 'M', 'ron.weasley@email.com', '$2a$10$q2NdHbCJCFUK0lrrH/AvzewClPYtj2sY2N5NerskRUGZpV5cMGTXS', true),
  ('Jon', 'Snow', '1986-12-08', 'M', 'jon.snow@email.com', '$2a$10$PV92SwbOfkUztIHFH87AM.JQChEcVlR4npMGJCTXI7C089rnOElLq', true),
  ('Daenerys', 'Targaryen', '1986-10-23', 'F', 'daenerys.targaryen@email.com', '$2a$10$oAseNdg9Roc70bOBucJin.68gf2AhT7dW3ZFQAbD1JKwFe.Wi5mM6', true),
  ('Tyrion', 'Lannister', '1980-11-27', 'M', 'tyrion.lannister@email.com', '$2a$10$v2Z8VbKRJsciouWGxxdtfeKzveb/.AtlgbyzHtu5a1t/OQU77v.R.', false),
  ('Luke', 'Skywalker', '1971-05-04', 'M', 'luke.skywalker@email.com', '$2a$10$xEsQR8wjNpp9/sBOeMG2buTMx9D3Q5/bqLzJ8cUeuqVFJF7poDsky', false),
  ('Leia', 'Organa', '1977-04-22', 'F', 'leia.organa@email.com', '$2a$10$4tFKmlG0qAYfCFilGUGacev8/cov95rltslXXI/zz6qfKUYmfNXFe', false),
  ('Han', 'Solo', '1973-01-29', 'M', 'han.solo@email.com', '$2a$10$Xw3pSwXT3vZA/aPnd0VwGusZTvOt74EmUgT7m0vnEGMNjbMX634K.', false),
  ('Arya', 'Stark', '2001-11-25', 'F', 'arya.stark@email.com', '$2a$10$DIz7wZoLPhpP0Y/tdy7rs.ZjeQsgKl5FrSUZrZXT9g6gWZdrpzJk6', false),
  ('Sansa', 'Stark', '1999-02-05', 'F', 'sansa.stark@email.com', '$2a$10$KluioJxYKP70qGWb8k7oN.crParkSpCMPD4r5u4DxZXrpjdoEj04C', false),
  ('Darth', 'Vader', '1977-09-13', 'M', 'darth.vader@email.com', '$2a$10$Gp5hXsq4ipEoCbzKWrsYhurnspCM10QGhHtY0mkK2nA3/zyhRw2C.', false),
  ('Obi-Wan', 'Kenobi', '1972-08-19', 'M', 'obiwan.kenobi@email.com', '$2a$10$m0aa4c7iIJbmcr/QU9kMo.PPeRJTeJAWkYBJhOS11CLG6POGrQmRC', false),
  ('Padmé', 'Amidala', '1974-04-21', 'F', 'padme.amidala@email.com', '$2a$10$dZhZe9nVD4T3ZoqjXfkImuV0zv/SLVyDdnWQWS.x0RqqyFX8qfZuS', false),
  ('Margaery', 'Tyrell', '1988-06-15', 'F', 'margaery.tyrell@email.com', '$2a$10$iFOwT2PyM8QwTRXAQkLKkOLdkZfpNCXaTppG68T2Mq6DL9R3BXQE.', false);
