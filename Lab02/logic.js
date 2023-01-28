window.addEventListener("DOMContentLoaded", () => {
  const tiles = Array.from(document.querySelectorAll(".tile"));

  //DisplayLabels
  const playerDisplay = document.querySelector(".display-player");
  const announcer = document.querySelector(".announcer");
  const xScoreDisplay = document.querySelector(".display-x");
  const oScoreDisplay = document.querySelector(".display-o");
  const tieScoreDisplay = document.querySelector(".display-tie");

  const PLAYERX_WON = "PLAYERX_WON";
  const PLAYERO_WON = "PLAYERO_WON";
  const TIE = "TIE";
  const ABANDON = "ABANDON";

  //Buttons
  const startButton = document.querySelector("#start");
  const abandonButton = document.querySelector("#abandon");
  const resetButton = document.querySelector("#reset"); //ResetGame
  const refreshButton = document.querySelector("#refresh"); //Refresh LocalStorage

  let board = ["", "", "", "", "", "", "", "", ""];
  let currentPlayer = "X";
  let isGameActive = false;

  var xScore = "0";
  var oScore = "0";
  var tieScore = "0";
  startButton.classList.remove("hide");
  const winningConditions = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6],
  ];

  //Default
  document.getElementById("info").innerHTML = "Click on start to begin playing";
  abandonButton.disabled = true;
  tiles.forEach((tile, index) => {
    tile.addEventListener("click", () => userAction(tile, index)); //adding eventListener
  });

  // localStorage NaN check --> move _API
  if (localStorage.getItem("X") === null) {
    localStorage.setItem("X", 0);
  } else {
    xScoreDisplay.innerText = localStorage.getItem("X");
  }

  if (localStorage.getItem("O") === null) {
    localStorage.setItem("O", 0);
  } else {
    oScoreDisplay.innerText = localStorage.getItem("O");
  }

  if (localStorage.getItem("TIE") === null) {
    localStorage.setItem("TIE", 0);
  } else {
    tieScoreDisplay.innerText = localStorage.getItem("TIE");
  }

  // Toggle color for localStorage scores
  if (parseInt(localStorage.getItem("X")) < 0) {
    xScoreDisplay.setAttribute("style", "color: #FF3860;");
  } else {
    xScoreDisplay.setAttribute("style", "color: #09C372;");
  }

  if (parseInt(localStorage.getItem("O")) < 0) {
    oScoreDisplay.setAttribute("style", "color: #FF3860;");
  } else {
    oScoreDisplay.setAttribute("style", "color: #09C372;");
  }

  //abandonButton - eventListener
  abandonButton.addEventListener("click", function () {
    isGameActive = false;
    abandonButton.classList.add("hide");
    announce(ABANDON);
    xScoreDisplay.innerText = xScore.toString();
    oScoreDisplay.innerText = oScore.toString();
    tieScoreDisplay.innerText = tieScore.toString();

    if (parseInt(xScore.toString()) < 0) {
      xScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      xScoreDisplay.setAttribute("style", "color: #09C372;");
    }

    if (parseInt(oScore.toString()) < 0) {
      oScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      oScoreDisplay.setAttribute("style", "color: #09C372;");
    }
  });

  //refreshButton - eventListener
  refreshButton.addEventListener("click", function () {
    localStorage.setItem("X", 0);
    localStorage.setItem("O", 0);
    localStorage.setItem("TIE", 0);
    xScoreDisplay.innerText = 0;
    oScoreDisplay.innerText = 0;
    tieScoreDisplay.innerText = 0;
    xScore = 0;
    oScore = 0;
    tieScore = 0;
    refreshPlayerScoreData();

    if (parseInt(xScore.toString()) < 0) {
      xScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      xScoreDisplay.setAttribute("style", "color: #09C372;");
    }

    if (parseInt(oScore.toString()) < 0) {
      oScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      oScoreDisplay.setAttribute("style", "color: #09C372;");
    }
    resetBoard();
    toggleBtns();
    document.getElementById("info").innerHTML =
      "Click on start to begin playing";
  });

  //startButton - eventListener
  startButton.addEventListener("click", function () {
    startButton.classList.add("hide");
    abandonButton.classList.remove("hide");
    resetButton.classList.remove("hide");
    isGameActive = true;
    abandonButton.disabled = false;
    startButton.disabled = true;
    abandonButton.setAttribute("style", "background-color: #FF3860;");
    startButton.setAttribute("style", "background-color: grey;");
    document.getElementById("info").innerHTML = "";
  });

  const toggleBtns = () => {
    abandonButton.disabled = true;
    startButton.disabled = false;
    abandonButton.setAttribute("style", "background-color: grey;");
    startButton.setAttribute("style", "background-color: #FF3860;");
  };

  function checkWinningCondition(winningConditions, board) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    //request payload
    var data = JSON.stringify({
      winningConditions: winningConditions,
      board: board,
    });

    var requestOptions = {
      method: "POST",
      headers: myHeaders,
      body: data,
    };

    var roundWon = false;

    fetch("http://localhost:5002/checkWinningCondition", requestOptions)
      .then(function (response) {
        response.json().then(function (data) {
          roundWon = data.status;
          if (roundWon) {
            announce(currentPlayer === "X" ? PLAYERX_WON : PLAYERO_WON);
            abandonButton.classList.add("hide");
            isGameActive = false;
            return;
          }

          if (!board.includes("")) {
            abandonButton.classList.add("hide");
            announce(TIE);
          }
        });
      })
      .catch((error) => {
        console.log("Enable offline game");
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

        if (roundWon) {
          announce(currentPlayer === "X" ? PLAYERX_WON : PLAYERO_WON);
          abandonButton.classList.add("hide");
          isGameActive = false;
          return;
        }

        if (!board.includes("")) {
          abandonButton.classList.add("hide");
          announce(TIE);
        }
      });
  }

  //validateBoard
  function handleResultValidation() {
    checkWinningCondition(winningConditions, board);
  }

  //displayResult
  const announce = (type) => {
    switch (type) {
      case PLAYERO_WON:
        announcer.innerHTML = 'Player <span class="playerX">X</span> Won';
        updatePlayerScoreData("X");
        break;
      case PLAYERX_WON:
        announcer.innerHTML = 'Player <span class="playerO">O</span> Won';
        updatePlayerScoreData("O");
        break;
      case TIE:
        announcer.innerText = "Tie";
        updatePlayerScoreData("TIE");
        break;
      case ABANDON:
        if (currentPlayer === "X") {
          updatePlayerScoreData("XX");
          announcer.innerHTML =
            'Player <span class="playerX">X</span> has abandoned! (-1 from score)';
        } else {
          updatePlayerScoreData("OO");
          announcer.innerHTML =
            'Player <span class="playerO">O</span> has abandoned! (-1 from score)';
        }
        break;
    }
    xScoreDisplay.innerText = xScore.toString();
    oScoreDisplay.innerText = oScore.toString();
    tieScoreDisplay.innerText = tieScore.toString();

    if (parseInt(xScore.toString()) < 0) {
      xScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      xScoreDisplay.setAttribute("style", "color: #09C372;");
    }

    if (parseInt(oScore.toString()) < 0) {
      oScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
      oScoreDisplay.setAttribute("style", "color: #09C372;");
    }
    abandonButton.disabled = true;
    abandonButton.setAttribute("style", "background-color: grey;");
    announcer.classList.remove("hide");
  };

  //check validTile
  const isValidAction = (tile) => {
    if (tile.innerText === "X" || tile.innerText === "O") {
      return false;
    }
    return true;
  };

  //update BoardArray
  const updateBoard = (index) => {
    board[index] = currentPlayer;
  };

  const changePlayer = () => {
    playerDisplay.classList.remove(`player${currentPlayer}`);
    currentPlayer = currentPlayer === "X" ? "O" : "X";
    playerDisplay.innerText = currentPlayer;
    playerDisplay.classList.add(`player${currentPlayer}`);
  };

  //triggered by actionPerformed on tile
  const userAction = (tile, index) => {
    if (isValidAction(tile) && isGameActive) {
      tile.innerText = currentPlayer;
      tile.classList.add(`player${currentPlayer}`);
      updateBoard(index);
      handleResultValidation();
      changePlayer();
    }
  };

  const resetBoard = () => {
    board = ["", "", "", "", "", "", "", "", ""];
    isGameActive = false;
    announcer.classList.add("hide");
    startButton.classList.remove("hide");
    resetButton.classList.add("hide");
    abandonButton.classList.add("hide");

    if (currentPlayer === "O") {
      changePlayer();
    }

    tiles.forEach((tile) => {
      tile.innerText = "";
      tile.classList.remove("playerX");
      tile.classList.remove("playerO");
    });

    toggleBtns();
    document.getElementById("info").innerHTML =
      "Click on start to begin playing";
  };
  resetButton.addEventListener("click", resetBoard);

  // -----------------------API-----------------------

  function refreshPlayerScoreData() {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptionsGet = {
      method: "GET",
      headers: myHeaders,
    };

    fetch("http://localhost:5002/refreshPlayerScoreData", requestOptionsGet)
      .then(async (response) => {
        const scoreData = await response.json();
        xScore = scoreData.xScore;
        oScore = scoreData.oScore;
        tieScore = scoreData.tieScore;

        xScoreDisplay.innerText = xScore.toString();
        oScoreDisplay.innerText = oScore.toString();
        tieScoreDisplay.innerText = tieScore.toString();

        if (parseInt(xScore.toString()) < 0) {
          xScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          xScoreDisplay.setAttribute("style", "color: #09C372;");
        }

        if (parseInt(oScore.toString()) < 0) {
          oScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          oScoreDisplay.setAttribute("style", "color: #09C372;");
        }
      })
      .catch((error) => {
        xScore = localStorage.getItem("X");
        oScore = localStorage.getItem("O");
        tieScore = localStorage.getItem("TIE");

        xScoreDisplay.innerText = xScore.toString();
        oScoreDisplay.innerText = oScore.toString();
        tieScoreDisplay.innerText = tieScore.toString();

        if (parseInt(xScore.toString()) < 0) {
          xScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          xScoreDisplay.setAttribute("style", "color: #09C372;");
        }

        if (parseInt(oScore.toString()) < 0) {
          oScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          oScoreDisplay.setAttribute("style", "color: #09C372;");
        }
      });
  }

  function updatePlayerScoreData(whoWon) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    //request payload
    var playerScore = JSON.stringify({
      winner: whoWon,
    });

    var requestOptions = {
      method: "POST",
      headers: myHeaders,
      body: playerScore,
    };

    fetch("http://localhost:5002/updatePlayerScoreData", requestOptions)
      .then(function (response) {
        response.json().then(function (data) {
          xScore = data.xScore;
          oScore = data.oScore;
          tieScore = data.tieScore;

          xScoreDisplay.innerText = xScore.toString();
          oScoreDisplay.innerText = oScore.toString();
          tieScoreDisplay.innerText = tieScore.toString();

          if (parseInt(xScore.toString()) < 0) {
            xScoreDisplay.setAttribute("style", "color: #FF3860;");
          } else {
            xScoreDisplay.setAttribute("style", "color: #09C372;");
          }

          if (parseInt(oScore.toString()) < 0) {
            oScoreDisplay.setAttribute("style", "color: #FF3860;");
          } else {
            oScoreDisplay.setAttribute("style", "color: #09C372;");
          }
        });
      })
      .catch((error) => {
        if (whoWon == "X") {
          var score = parseInt(localStorage.getItem("X")) + 1;
          localStorage.setItem("X", score);
          xScore = score;
        } else if (whoWon == "O") {
          var score = parseInt(localStorage.getItem("O")) + 1;
          localStorage.setItem("O", score);
          oScore = score;
        } else if (whoWon == "TIE") {
          var score = parseInt(localStorage.getItem("TIE")) + 1;
          localStorage.setItem("TIE", score);
          tieScore = score;
        } else {
          localStorage.setItem(
            currentPlayer,
            parseInt(localStorage.getItem(currentPlayer)) - 1
          );
        }
        xScoreDisplay.innerText = localStorage.getItem("X");
        oScoreDisplay.innerText = localStorage.getItem("O");
        tieScoreDisplay.innerText = localStorage.getItem("TIE");

        if (parseInt(localStorage.getItem("X")) < 0) {
          xScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          xScoreDisplay.setAttribute("style", "color: #09C372;");
        }

        if (parseInt(localStorage.getItem("O")) < 0) {
          oScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
          oScoreDisplay.setAttribute("style", "color: #09C372;");
        }
      });
  }
});
