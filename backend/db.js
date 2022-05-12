// NOTE: Database user has to have a password, otherwise backend will throw an error.
const mysql = require("mysql2");

const pool = mysql.createPool({
  user: "",
  password: "", 
  host: "", 
  port: ,
  database: "geogame",
});

module.exports = { pool };
