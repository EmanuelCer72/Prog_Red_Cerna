TP Final - TicTacToe por red
Archivos:
Celda.java
Tablero.java
Jugador.java
Server.java
Client.java

Compilar:
javac *.java

Ejecutar servidor:
java Server

Ejecutar cliente (dos instancias en consolas distintas):
java Client

Protocolo simple por líneas de texto:
REQUEST_NAME, NAME|<nombre>, ROLE|X/O, START|<oponente>, BOARD|<tablerocon\n>, YOUR_TURN, WAIT, MOVE|r|c, INVALID, RESULT|WIN/LOSE/DRAW, END|...

