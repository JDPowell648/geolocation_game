const host = "coms-319-g16.class.las.iastate.edu";
const user = "root";
const password = "62b89689a6f847cc";

const Importer = require("mysql-import");
const importer = new Importer({ host, user, password });

importer
  .import("./Geogame_SQL/createTestDbTables.sql")
  .then(() => {
    console.log("Created test database");
    importer.disconnect(false);
  })
  .catch((err) => {
    console.log(err);
  });
