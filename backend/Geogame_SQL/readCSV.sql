use geogame;
SET GLOBAL local_infile = 'ON';
LOAD DATA LOCAL INFILE "C:/Users/Josh/Documents/Geogame_SQL/MOCK_DATA.csv"
INTO TABLE USERS FIELDS TERMINATED BY "," OPTIONALLY ENCLOSED BY '"'
LINES TERMINATED BY "\n"
IGNORE 1 LINES
(`name`,`rank`,gamesPlayed,averageScore,accuracy);