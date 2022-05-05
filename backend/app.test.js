const request = require("supertest");
const a = require("./app");
a.setTestingEnv();
const app = a.app;

describe("Test Endpoint", () => {
  it("POST /test should return 200 status", async () => {
    const response = await request(app).post("/test");
    expect(response.status).toEqual(200);
  });
});

describe("Create Account Endpoint", () => {
  const testUserName = "testcreateaccountuser";
  const testUsePassword = "testing123";

  it("POST /create_account should create an account and successfully login", async () => {
    const res = await request(app)
      .post("/create_account")
      .query({ format: "json" })
      .send({
        name: testUserName,
        password: testUsePassword,
      });

    expect(res.body).toHaveProperty("Message");
    expect(res.body).not.toHaveProperty("Error");
  });

  it("POST /login should update sessionId and expires then respond with a valid sessionId", async () => {
    const response = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: testUserName,
        password: testUsePassword,
      })
      .retry(2);

    expect(response.body).not.toHaveProperty("Error");
    expect(response.body).toHaveProperty("sessionId");
    expect(response.body.sessionId).not.toBe(null);
    expect(response.body.sessionId).not.toBe(undefined);
    expect(response.body.sessionId.length).not.toBe(0);
  });
});

describe("Login Endpoint", () => {
  it("POST /login should update sessionId and expires then respond with a valid sessionId", async () => {
    const res = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "pass",
      });

    expect(res.body).not.toHaveProperty("Error");
    expect(res.body).toHaveProperty("sessionId");
    expect(res.body.sessionId).not.toBe(null);
    expect(res.body.sessionId.length).not.toBe(0);
  });

  it("POST /login with wrong password, should respond with an error", async () => {
    const res = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "wrongpass",
      });

    expect(res.body).toHaveProperty("Error");
    expect(res.body).not.toHaveProperty("sessionId");
  });
});

describe("Logout Endpoint", () => {
  it("POST /logout should update sessionId and expires to be null then send a 200 response", async () => {
    const loginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "pass",
      });

    const sid = loginResponse.body.sessionId;

    const response = await request(app).post("/logout").send({
      name: "dev",
      sessionId: sid,
    });

    expect(response.body).toHaveProperty("Message");
    expect(response.body).not.toHaveProperty("Error");
  });
});

describe("Reset Password Endpoint", () => {
  it("POST /reset_password should update a users password given an old password, new password, and username.", async () => {
    const resetPasswordResponse = await request(app)
      .post("/reset_password")
      .query({ format: "json" })
      .send({
        name: "dev",
        newPassword: "newPassword",
        oldPassword: "pass",
      });

    expect(resetPasswordResponse.body).toHaveProperty("Message");
    expect(resetPasswordResponse.body).not.toHaveProperty("Error");

    const loginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "newPassword",
      });

    expect(loginResponse.body).toHaveProperty("sessionId");
    expect(loginResponse.body.sessionId).not.toBe(null);
    expect(loginResponse.body.sessionId.length).not.toBe(0);

    const response = await request(app)
      .post("/reset_password")
      .query({ format: "json" })
      .send({
        name: "dev",
        newPassword: "pass",
        oldPassword: "newPassword",
      });

    expect(response.body).toHaveProperty("Message");
    expect(response.body).not.toHaveProperty("Error");
  });
});

describe("User Data Endpoint", () => {
  it("GET /user_data", async () => {
    // This endpoint is for retrieving existing data
    const loginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "pass",
      });

    expect(loginResponse.body).not.toHaveProperty("Error");
    expect(loginResponse.body).toHaveProperty("sessionId");
    expect(loginResponse.body.sessionId.length).not.toBe(0);

    const userDataResponse = await request(app).get("/user_data").send({
      name: "dev",
      sessionId: loginResponse.body.sessionId,
    });

    expect(userDataResponse.body).not.toHaveProperty("Error");
    expect(userDataResponse.body).toHaveProperty("rank");
    expect(userDataResponse.body.rank.length).not.toBe(0);
    expect(userDataResponse.body).toHaveProperty("gamesPlayed");
    expect(userDataResponse.body.gamesPlayed.length).not.toBe(0);
    expect(userDataResponse.body).toHaveProperty("averageScore");
    expect(userDataResponse.body.averageScore.length).not.toBe(0);
    expect(userDataResponse.body).toHaveProperty("accuracy");
    expect(userDataResponse.body.accuracy.length).not.toBe(0);
  });
});

describe("Leaderboard Request Endpoint", () => {
  it("POST /leaderboard_request should get leaderboard information for first ranking player 'dev'", async () => {
    const leaderBoardRes = await request(app)
      .post("/leaderboard_request")
      .query({ format: "json" })
      .send({
        rank: "1",
      });

    expect(leaderBoardRes.body).not.toHaveProperty("Error");
    expect(leaderBoardRes.body.name).toBe("dev");

    expect(leaderBoardRes.body).toHaveProperty("gamesPlayed");
    expect(leaderBoardRes.body.gamesPlayed).toBe(0);

    expect(leaderBoardRes.body).toHaveProperty("averageScore");
    expect(leaderBoardRes.body.averageScore).toBe(0);

    expect(leaderBoardRes.body).toHaveProperty("accuracy");
    expect(leaderBoardRes.body.accuracy).toBe(0);
  });

  it("POST /leaderboard_request should get leaderboard information for second ranking player 'testcreateaccountuser'", async () => {
    const leaderBoardRes = await request(app)
      .post("/leaderboard_request")
      .query({ format: "json" })
      .send({
        rank: "2",
      });

    expect(leaderBoardRes.body).not.toHaveProperty("Error");
    expect(leaderBoardRes.body.name).toBe("testcreateaccountuser");

    expect(leaderBoardRes.body).toHaveProperty("gamesPlayed");
    expect(leaderBoardRes.body.gamesPlayed).toBe(0);

    expect(leaderBoardRes.body).toHaveProperty("averageScore");
    expect(leaderBoardRes.body.averageScore).toBe(0);

    expect(leaderBoardRes.body).toHaveProperty("accuracy");
    expect(leaderBoardRes.body.accuracy).toBe(0);
  });
});

describe("Num Users Endpoint", () => {
  it("POST /num_users should the total number of users in the LOGININFO table, there should be 2 users.", async () => {
    const res = await request(app).post("/num_users").query({ format: "json" });

    expect(res.body).not.toHaveProperty("Error");
    expect(res.body).toHaveProperty("result");
    expect(res.body.result).toBe(2);
  });
});

describe("Num Locations Endpoint", () => {
  it("POST /num_locations should return the number of locations in the LOCATION table, there should be 1.", async () => {
    const res = await request(app)
      .post("/num_locations")
      .query({ format: "json" });

    expect(res.body).not.toHaveProperty("Error");
    expect(res.body).toHaveProperty("result");
    expect(res.body.result).toBe(1);
  });
});

describe("Create Game Endpoint", () => {
  it("POST /create_game should insert a new 'game' or location.", async () => {
    const res = await request(app)
      .post("/create_game")
      .query({ format: "json" })
      .send({
        id: "2",
        latitude: "45",
        longitude: "-93",
      });

    expect(res.body).not.toHaveProperty("Error");
    expect(res.body).toHaveProperty("Message");
  });

  it("POST /create_game should return an error given an id already in use.", async () => {
    const res = await request(app)
      .post("/create_game")
      .query({ format: "json" })
      .send({
        id: "2",
        latitude: "45",
        longitude: "-93",
      });

    expect(res.body).toHaveProperty("Error");
    expect(res.body).not.toHaveProperty("Message");
  });
});

describe("Get Random Game Endpoint", () => {
  it("POST /get_random_game should return the location with id of 2.", async () => {
    const res = await request(app)
      .post("/get_random_game")
      .query({ format: "json" })
      .send({
        id: "2",
      });

    expect(res.body).not.toHaveProperty("Error");
    expect(res.body).toHaveProperty("latitude");
    expect(res.body.latitude).toBe(45);

    expect(res.body).toHaveProperty("longitude");
    expect(res.body.longitude).toBe(-93);
  });
});

describe("Update User Data Endpoint", () => {
  it("POST /update_user_data should update the table with the new given data.", async () => {
    const res = await request(app)
      .post("/update_user_data")
      .query({ format: "json" })
      .send({
        name: "dev",
        rank: "2",
        averageScore: "1",
        gamesPlayed: "5",
        accuracy: "2",
      });

    expect(res.body).toHaveProperty("Message");
    expect(res.body).not.toHaveProperty("Error");
  });

  it("GET /user_data table should have been updated from the last update_user_data call.", async () => {
    const loginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "pass",
      })
      .retry(2);

    expect(loginResponse.body).not.toHaveProperty("Error");
    expect(loginResponse.body).toHaveProperty("sessionId");
    expect(loginResponse.body.sessionId.length).not.toBe(0);

    const userDataResponse = await request(app).get("/user_data").send({
      name: "dev",
      sessionId: loginResponse.body.sessionId,
    });

    expect(userDataResponse.body).toHaveProperty("rank");
    expect(userDataResponse.body.rank).toBe(1); // NOTE: should not affect rank.

    expect(userDataResponse.body).toHaveProperty("averageScore");
    expect(userDataResponse.body.averageScore).toBe(1);

    expect(userDataResponse.body).toHaveProperty("gamesPlayed");
    expect(userDataResponse.body.gamesPlayed).toBe(5);

    expect(userDataResponse.body).toHaveProperty("accuracy");
    expect(userDataResponse.body.accuracy).toBe(2);
  });

  it("POST /update_user_data should update the table with the new given data.", async () => {
    const res = await request(app)
      .post("/update_user_data")
      .query({ format: "json" })
      .send({
        name: "testcreateaccountuser",
        rank: "2",
        averageScore: "6000",
        gamesPlayed: "10",
        accuracy: "0",
      });

    expect(res.body).toHaveProperty("Message");
    expect(res.body).not.toHaveProperty("Error");
  });

  it("GET /user_data table should have been updated.", async () => {
    const loginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "dev",
        password: "pass",
      });

    expect(loginResponse.body).not.toHaveProperty("Error");
    expect(loginResponse.body).toHaveProperty("sessionId");
    expect(loginResponse.body.sessionId.length).not.toBe(0);

    const testLoginResponse = await request(app)
      .post("/login")
      .query({ format: "json" })
      .send({
        name: "testcreateaccountuser",
        password: "testing123",
      });

    expect(testLoginResponse.body).not.toHaveProperty("Error");
    expect(testLoginResponse.body).toHaveProperty("sessionId");
    expect(testLoginResponse.body.sessionId.length).not.toBe(0);

    const devDataResponse = await request(app).get("/user_data").send({
      name: "dev",
      sessionId: loginResponse.body.sessionId,
    });

    const testcreateaccountuserDataResponse = await request(app)
      .get("/user_data")
      .send({
        name: "testcreateaccountuser",
        sessionId: testLoginResponse.body.sessionId,
      });

    expect(devDataResponse.body).toHaveProperty("rank");
    expect(devDataResponse.body.rank).toBe(2);

    expect(devDataResponse.body).toHaveProperty("averageScore");
    expect(devDataResponse.body.averageScore).toBe(1);

    expect(devDataResponse.body).toHaveProperty("gamesPlayed");
    expect(devDataResponse.body.gamesPlayed).toBe(5);

    expect(devDataResponse.body).toHaveProperty("accuracy");
    expect(devDataResponse.body.accuracy).toBe(2);

    /* --- */

    expect(testcreateaccountuserDataResponse.body).toHaveProperty("rank");
    expect(testcreateaccountuserDataResponse.body.rank).toBe(1);

    expect(testcreateaccountuserDataResponse.body).toHaveProperty(
      "averageScore"
    );
    expect(testcreateaccountuserDataResponse.body.averageScore).toBe(6000);

    expect(testcreateaccountuserDataResponse.body).toHaveProperty(
      "gamesPlayed"
    );
    expect(testcreateaccountuserDataResponse.body.gamesPlayed).toBe(10);

    expect(testcreateaccountuserDataResponse.body).toHaveProperty("accuracy");
    expect(testcreateaccountuserDataResponse.body.accuracy).toBe(0);
  });
});
