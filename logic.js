window.addEventListener('DOMContentLoaded', () => {
    const tiles = Array.from(document.querySelectorAll('.tile'));
    const playerDisplay = document.querySelector('.display-player');
    const resetButton = document.querySelector('#reset');
    const startButton = document.querySelector('#start');
    const abandonButton = document.querySelector('#abandon');
    const refreshButton = document.querySelector('#refresh');
    const announcer = document.querySelector('.announcer');
    const xScoreDisplay = document.querySelector('.display-x');
    const oScoreDisplay = document.querySelector('.display-o');
    const tieScoreDisplay = document.querySelector('.display-tie');
    document.getElementById("info").innerHTML = "Click on start to begin playing"
    // localStorage NaN check
    if(localStorage.getItem("X") === null){
        localStorage.setItem("X", 0)
    }

    if(localStorage.getItem("O") === null){
        localStorage.setItem("O", 0)
    }

    if(localStorage.getItem("TIE") === null){
        localStorage.setItem("TIE", 0)
    }

    // Access localStorage values
    xScoreDisplay.innerText = localStorage.getItem("X");
    oScoreDisplay.innerText = localStorage.getItem("O");
    tieScoreDisplay.innerText = localStorage.getItem("TIE");

    let board = ['', '', '', '', '', '', '', '', ''];
    let currentPlayer = 'X';
    let isGameActive = false;

    const PLAYERX_WON = 'PLAYERX_WON';
    const PLAYERO_WON = 'PLAYERO_WON';
    const TIE = 'TIE';
    const ABANDON = 'ABANDON';

    const winningConditions = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6]
    ];


    // Toggle color for scores
    if(parseInt(localStorage.getItem("X"))<0){
        xScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
        xScoreDisplay.setAttribute("style", "color: #09C372;");
    }

    if(parseInt(localStorage.getItem("O"))<0){
        oScoreDisplay.setAttribute("style", "color: #FF3860;");
    } else {
        oScoreDisplay.setAttribute("style", "color: #09C372;");
    }

    // resetButton.disabled = true;
    abandonButton.disabled = true;

    // Enable abandon button after startGame
    startButton.addEventListener("click", function(){
        abandonButton.disabled = false;
        startButton.disabled = true;
        abandonButton.setAttribute("style", "background-color: #FF3860;");
        startButton.setAttribute("style", "background-color: grey;");
        document.getElementById("info").innerHTML = "";
    });

    // Enable startGame button after player has abandoned the game
    abandonButton.addEventListener("click", function(){
        toggleBtns();
    });

    resetButton.addEventListener("click", function(){
        toggleBtns();
        document.getElementById('info').innerHTML = 'Click on start to begin playing'
    });

    const toggleBtns =  () => {
        abandonButton.disabled = true;
        startButton.disabled = false;
        abandonButton.setAttribute("style", "background-color: grey;");
        startButton.setAttribute("style", "background-color: #FF3860;");
    }

    function handleResultValidation() {
        let roundWon = false;
        for (let i = 0; i <= 7; i++) {
            const winCondition = winningConditions[i];
            const a = board[winCondition[0]];
            const b = board[winCondition[1]];
            const c = board[winCondition[2]];
            if (a === '' || b === '' || c === '') {
                continue;
            }
            if (a === b && b === c) {
                roundWon = true;
                break;
            }
        }

    if (roundWon) {
            announce(currentPlayer === 'X' ? PLAYERX_WON : PLAYERO_WON);
            isGameActive = false;
            return;
        }

    if (!board.includes(''))
        announce(TIE);
    }

    const announce = (type) => {
        switch(type){
            case PLAYERO_WON:
                announcer.innerHTML = 'Player <span class="playerO">O</span> Won';
                var score = parseInt(localStorage.getItem("O"))+1;
                localStorage.setItem("O", score)
                break;
            case PLAYERX_WON:
                announcer.innerHTML = 'Player <span class="playerX">X</span> Won';
                var score = parseInt(localStorage.getItem("X"))+1;
                localStorage.setItem("X", score)
                break;
            case TIE:
                announcer.innerText = 'Tie';
                var score = parseInt(localStorage.getItem("TIE"))+1;
                localStorage.setItem("TIE", score)
                break;
            case ABANDON:
                if(currentPlayer === 'X'){
                    announcer.innerHTML = 'Player <span class="playerX">X</span> has abandoned! (-1 from score)'; 
                } else{
                    announcer.innerHTML = 'Player <span class="playerO">O</span> has abandoned! (-1 from score)';
                }
                break;
        }
        updateScores();
        toggleBtns();
        announcer.classList.remove('hide');
    };

    const isValidAction = (tile) => {
        if (tile.innerText === 'X' || tile.innerText === 'O'){
            return false;
        }

        return true;
    };

    const updateBoard =  (index) => {
        board[index] = currentPlayer;
    }

    const changePlayer = () => {
        playerDisplay.classList.remove(`player${currentPlayer}`);
        currentPlayer = currentPlayer === 'X' ? 'O' : 'X';
        playerDisplay.innerText = currentPlayer;
        playerDisplay.classList.add(`player${currentPlayer}`);
    }

    const updateScores = () => {
        xScoreDisplay.innerText = localStorage.getItem("X");
        oScoreDisplay.innerText = localStorage.getItem("O");
        tieScoreDisplay.innerText = localStorage.getItem("TIE");

        if(parseInt(localStorage.getItem("X"))<0){
            xScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
            xScoreDisplay.setAttribute("style", "color: #09C372;;");
        }

        if(parseInt(localStorage.getItem("O"))<0){
            oScoreDisplay.setAttribute("style", "color: #FF3860;");
        } else {
            oScoreDisplay.setAttribute("style", "color: #09C372;;");
        }
    }

    const userAction = (tile, index) => {
        if(isValidAction(tile) && isGameActive) {
            tile.innerText = currentPlayer;
            tile.classList.add(`player${currentPlayer}`);
            updateBoard(index);
            handleResultValidation();
            changePlayer();
        }
    }
    
    const resetBoard = () => {
        board = ['', '', '', '', '', '', '', '', ''];
        isGameActive = false;
        announcer.classList.add('hide');

        if (currentPlayer === 'O') {
            changePlayer();
        }

        tiles.forEach(tile => {
            tile.innerText = '';
            tile.classList.remove('playerX');
            tile.classList.remove('playerO');
        });
    }

    tiles.forEach( (tile, index) => {
        tile.addEventListener('click', () => userAction(tile, index));
    });

    const startGame = () => {
        isGameActive = true;
        // resetBoard();
    }

    // Abandon Game - lose points
    const abandonGame = () => {
        
        isGameActive = false;
        let player = currentPlayer;
        var score = parseInt(localStorage.getItem(player))-1;
        localStorage.setItem(player, score);
        announce(ABANDON);
        updateScores();
        // resetBoard();
        // console.log("abandon on " + player);
    }

    const refreshScore = () => {
        localStorage.setItem("X", 0);
        localStorage.setItem("O", 0);
        localStorage.setItem("TIE", 0);
        updateScores();
    }
    

    startButton.addEventListener('click', startGame);
    abandonButton.addEventListener('click', abandonGame);
    resetButton.addEventListener('click', resetBoard);
    refreshButton.addEventListener('click', refreshScore);
});