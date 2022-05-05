// NOTE: Database user has to have a password, otherwise backend will throw an error.
const mysql = require("mysql2");

const pool = mysql.createPool({
  user: "root",
  password: "62b89689a6f847cc", //62b89689a6f847cc
  host: "coms-319-g16.class.las.iastate.edu", //coms-319-g16.class.las.iastate.edu
  port: 3306,
  database: "geogame",
});

const testPool = mysql.createPool({
  user: "root",
  password: "62b89689a6f847cc", //62b89689a6f847cc
  host: "coms-319-g16.class.las.iastate.edu",
  port: 3306,
  database: "testdb",
});

module.exports = { pool, testPool };
