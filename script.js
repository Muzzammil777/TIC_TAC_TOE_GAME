const X = "X";
const O = "O";
let currentPlayer = X;
let board = [
  ["", "", ""],
  ["", "", ""],
  ["", "", ""],
];
let playWithBot = false;

const boardDiv = document.getElementById("board");
const playerTurnDiv = document.getElementById("playerTurn");
const resultModal = document.getElementById("resultModal");
const resultText = document.getElementById("resultText");
const restartBtn = document.getElementById("restartBtn");
const choiceModal = document.getElementById("choiceModal");
const humanBtn = document.getElementById("humanBtn");
const botBtn = document.getElementById("botBtn");

function showChoiceModal() {
  choiceModal.style.display = "flex";
}
function hideChoiceModal() {
  choiceModal.style.display = "none";
}
humanBtn.onclick = () => {
  playWithBot = false;
  hideChoiceModal();
  startGame();
};
botBtn.onclick = () => {
  playWithBot = true;
  hideChoiceModal();
  startGame();
};
function startGame() {
  currentPlayer = X;
  board = [
    ["", "", ""],
    ["", "", ""],
    ["", "", ""],
  ];
  renderBoard();
  updatePlayerTurn();
}
function renderBoard() {
  boardDiv.innerHTML = "";
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      const cell = document.createElement("div");
      cell.className = "cell";
      if (board[i][j] === X) cell.classList.add("x");
      if (board[i][j] === O) cell.classList.add("o");
      cell.textContent = board[i][j];
      cell.onclick = () => handleCellClick(i, j);
      boardDiv.appendChild(cell);
    }
  }
}
function updatePlayerTurn() {
  playerTurnDiv.textContent = `Player ${currentPlayer}'s turn`;
}
function handleCellClick(i, j) {
  if (board[i][j] !== "" || resultModal.style.display === "flex") return;
  board[i][j] = currentPlayer;
  renderBoard();
  if (checkWin()) {
    showResult(`Player ${currentPlayer} wins!`);
    return;
  } else if (checkDraw()) {
    showResult("The game is a draw!");
    return;
  }
  currentPlayer = currentPlayer === X ? O : X;
  updatePlayerTurn();
  if (playWithBot && currentPlayer === O) {
    setTimeout(botMove, 400);
  }
}
function botMove() {
  // Simple random move bot
  let emptyCells = [];
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      if (board[i][j] === "") emptyCells.push([i, j]);
    }
  }
  if (emptyCells.length === 0) return;
  const [i, j] = emptyCells[Math.floor(Math.random() * emptyCells.length)];
  board[i][j] = O;
  renderBoard();
  if (checkWin()) {
    showResult(`Player ${O} wins!`);
    return;
  } else if (checkDraw()) {
    showResult("The game is a draw!");
    return;
  }
  currentPlayer = X;
  updatePlayerTurn();
}
function checkWin() {
  for (let i = 0; i < 3; i++) {
    if (
      board[i][0] &&
      board[i][0] === board[i][1] &&
      board[i][1] === board[i][2]
    )
      return true;
    if (
      board[0][i] &&
      board[0][i] === board[1][i] &&
      board[1][i] === board[2][i]
    )
      return true;
  }
  if (board[0][0] && board[0][0] === board[1][1] && board[1][1] === board[2][2])
    return true;
  if (board[0][2] && board[0][2] === board[1][1] && board[1][1] === board[2][0])
    return true;
  return false;
}
function checkDraw() {
  for (let i = 0; i < 3; i++) {
    for (let j = 0; j < 3; j++) {
      if (board[i][j] === "") return false;
    }
  }
  return true;
}
function showResult(message) {
  resultText.textContent = message;
  resultModal.style.display = "flex";
}
restartBtn.onclick = () => {
  resultModal.style.display = "none";
  showChoiceModal();
};
// Start with choice modal
showChoiceModal();
