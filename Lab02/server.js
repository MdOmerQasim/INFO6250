const express = require("express");
const bodyParser = require("body-parser");
const app = express(); //Instantiate an express app, the main work horse of this server
const port = 5002; //Save the port number where your server will be listening
const cors = require("cors");

app.use(cors()); //Enable cross origin resource sharing

var winner_history = [];

app.get("/refreshPlayerScoreData", function (req, res) {
  winner_history = [];

  var response = JSON.stringify({
    status: "success",
    xScore: 0,
    oScore: 0,
    tieScore: 0,
  });

  res.send(response);
});

app.post("/updatePlayerScoreData", bodyParser.json(), function (req, res) {
  res.setHeader("Content-Type", "application/json"); // Sets the Header

  winner_history.push(req.body.winner);
  let xScore = winner_history.filter((x) => x == "X").length;
  let oScore = winner_history.filter((x) => x == "O").length;
  let tieScore = winner_history.filter((x) => x == "TIE").length;

  let minusXScore = winner_history.filter((x) => x == "XX").length;
  let minusOScore = winner_history.filter((x) => x == "OO").length;

  let xFinalScore = xScore - minusXScore;
  let oFinalScore = oScore - minusOScore;

  //response payload
  var response = JSON.stringify({
    status: "success",
    xScore: xFinalScore,
    oScore: oFinalScore,
    tieScore: tieScore,
  });

  res.send(response);
});

app.listen(port, () => {
  //server starts listening for any attempts from a client to connect at port: {port}
  console.log(`Now listening on port ${port}`);
});

app.post("/checkWinningCondition", bodyParser.json(), function (req, res) {
  res.setHeader("Content-Type", "application/json"); // Sets the Header

  let board = req.body.board;
  let winningConditions = req.body.winningConditions;

  let roundWon = false;
  for (let i = 0; i <= 7; i++) {
    const winCondition = winningConditions[i];
    const a = board[winCondition[0]];
    const b = board[winCondition[1]];
    const c = board[winCondition[2]];
    if (a === "" || b === "" || c === "") {
      continue;
    }
    if (a === b && b === c) {
      roundWon = true;
      break;
    }
  }

  //response payload
  var response = JSON.stringify({
    status: roundWon,
  });

  res.send(response);
});
