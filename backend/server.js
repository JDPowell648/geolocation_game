const app = require("./app.js").app;

// NOTE: Port may need to be changed
const PORT = 5000;

app.listen(PORT, () => {
  console.log("Server has started on port " + PORT);
});
