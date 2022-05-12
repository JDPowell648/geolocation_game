const host = "";
const user = "";
const password = "";

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
