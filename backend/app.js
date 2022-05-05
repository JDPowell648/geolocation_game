const express = require("express");
const mysql = require("mysql2");
const cors = require("cors");
const bcrypt = require("bcryptjs");
const crypto = require("crypto");
const dataBaseConfig = require("./db");
const app = express();
const minimist = require("minimist");
var pool = dataBaseConfig.pool;
var isTesting = false;

function setTestingEnv() {
  pool = dataBaseConfig.testPool;
  isTesting = true;
}

//Middleware
app.use(cors());
app.use(express.json());

app.post("/test", async (req, res) => {
  res.sendStatus(200);
});

app.post("/login", async (req, res) => {
  try {
    pool.query(
      "SELECT * FROM LOGININFO WHERE name = ?",
      [req.body.name],
      (err, result) => {
        if (err) {
          res.json({ Error: err });
        }
        bcrypt.compare(
          req.body.password,
          result[0].password,
          (compare_error, compare_result) => {
            if (compare_error) {
              res.json({ Error: compare_error });
            }
            if (compare_result) {
              const id = crypto.randomBytes(50).toString("hex");
              pool.query(
                "UPDATE LOGININFO SET sessionId = ?, expires = (current_date + 1) WHERE name = ?",
                [id, req.body.name]
              );
              res.json({
                sessionId: id,
              });
            } else {
              res.json({ Error: "Wrong Password" });
            }
          }
        );
      }
    );
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/logout", async (req, res) => {
  const username = req.body.name;
  const sessionId = req.body.sessionId;

  try {
    pool.query(
      "SELECT * FROM LOGININFO WHERE name = ?",
      [username],
      (err, response) => {
        if (err) {
          res.json({ Error: err });
        }

        if (response[0].sessionId === sessionId) {
          pool.query(
            "UPDATE LOGININFO SET sessionId = NULL, expires = NULL WHERE name = ?",
            [username]
          );
        }
        res.json({ Message: "Successfully logged out" });
      }
    );
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/reset_password", async (req, res) => {
  try {
    pool.query(
      "SELECT * FROM LOGININFO WHERE name = ?",
      [req.body.name],
      (err, result) => {
        bcrypt.compare(
          req.body.oldPassword,
          result[0].password,
          (e, compare_res) => {
            if (e) {
              throw e;
            }
            if (compare_res) {
              bcrypt.hash(req.body.newPassword, 10, (error, hash) => {
                pool.query(
                  "UPDATE LOGININFO SET password = ? WHERE name = ?",
                  [hash, req.body.name],
                  () => {
                    res.json({ Message: "Password has been reset" });
                  }
                );
              });
            } else {
              res.json({ Error: "Authenticate again" });
            }
          }
        );
      }
    );
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/create_account", async (req, res) => {
  const username = req.body.name;
  const pass = req.body.password;

  try {
    await bcrypt.hash(pass, 10, (err, hash) => {
      if (err) {
        res.json({ Error: err });
      }

      pool.query("INSERT INTO LOGININFO(name, password) VALUES(?, ?)", [
        username,
        hash,
      ]);

      pool.query("SELECT COUNT(*) AS result FROM USERS", (err, countRes) => {
        if (err) {
          res.json({ Error: err });
        }
        pool.query(
          "INSERT INTO USERS(`name`,`rank`,gamesPlayed,averageScore,accuracy) VALUES(?,?,0,0,0)",
          [username, countRes[0].result + 1]
        );

        res.json({ Message: "Account has been successfully created" });
      });
    });
  } catch (err) {
    res.json({ Error: e });
  }
});

app.get("/user_data", (req, response) => {
  const sessionId = req.body.sessionId;
  const username = req.body.name;

  try {
    pool.query("SELECT * FROM LOGININFO WHERE name = ?", [username], (e, r) => {
      if (e) {
        response.json({ Error: e });
      }
      pool.query(
        "SELECT * FROM USERS WHERE name = ?",
        [username],
        (err, res) => {
          if (err) {
            throw err;
          }

          response.json({
            rank: res[0].rank,
            gamesPlayed: res[0].gamesPlayed,
            averageScore: res[0].averageScore,
            accuracy: res[0].accuracy,
          });
        }
      );
    });
  } catch (e) {
    response.json({ Error: e });
  }
});

app.post("/leaderboard_request", (req, response) => {
  const rank = req.body.rank;

  try {
    pool.query("SELECT * FROM USERS WHERE `rank` = ?", [rank], (e, res) => {
      if (e) {
        res.json({ Error: e });
      }
      response.json({
        name: res[0].name,
        gamesPlayed: res[0].gamesPlayed,
        averageScore: res[0].averageScore,
        accuracy: res[0].accuracy,
      });
    });
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/num_users", (req, response) => {
  try {
    pool.query("SELECT COUNT(*) AS result FROM USERS", (e, res) => {
      if (e) {
        throw e;
      }
      response.json({
        result: res[0].result,
      });
    });
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/num_locations", (req, response) => {
  try {
    pool.query("SELECT COUNT(*) AS result FROM LOCATION", (e, res) => {
      if (e) {
        res.json({ Error: e });
      }
      response.json({
        result: res[0].result,
      });
    });
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/create_game", async (req, res) => {
  const id = req.body.id;
  const lat = req.body.latitude;
  const long = req.body.longitude;

  try {
    pool.query(
      "INSERT INTO LOCATION(location_id, latitude, longitude) VALUES(?, ?, ?)",
      [id, lat, long],
      (err, queryRes) => {
        if (err) {
          res.json({ Error: err });
        } else {
          res.json({ Message: "Success" });
        }
      }
    );
  } catch (err) {
    res.json({ Error: err });
  }
});

app.post("/get_random_game", (req, response) => {
  const id = req.body.id;

  try {
    pool.query(
      "SELECT * FROM LOCATION WHERE location_id = ?",
      [id],
      (e, res) => {
        if (e) {
          throw e;
        }
        response.json({
          latitude: res[0].latitude,
          longitude: res[0].longitude,
        });
      }
    );
  } catch (e) {
    res.json({ Error: e });
  }
});

app.post("/update_user_data", async (req, res) => {
  const username = req.body.name;
  const rank = req.body.rank;
  const averageScore = req.body.averageScore;
  const gamesPlayed = req.body.gamesPlayed;
  const accuracy = req.body.accuracy;

  try {
    pool.query(
      "UPDATE USERS SET `rank` = ?, gamesPlayed = ?, averageScore = ?, accuracy = ? WHERE `name` = ?",
      [rank, gamesPlayed, averageScore, accuracy, username]
    );
    pool.query("CALL updateRank");

    res.json({ Message: "Success" });
  } catch (err) {
    res.json({ Error: err });
  }
});

module.exports = { app, setTestingEnv };
