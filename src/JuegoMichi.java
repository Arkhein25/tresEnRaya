/*podrias crear un juego de gato en java un array de 3x3 donde el usuario sea la x y el ordenador la o
 *
 * utiliza esta ayuda
 * int numero=(int) (Math.random()*3)+1;
 * System.out.println(numero);
 * */

import java.util.Scanner;

public class JuegoMichi {
    private char[][] tablero = new char[3][3]; // Mala práctica
    private Scanner scanner = new Scanner(System.in); //Ídem
    private EstadoJuego estadoJuego = EstadoJuego.EN_CURSO;

    public static void main(String[] args) {
        JuegoMichi juego = new JuegoMichi();
        juego.iniciarJuego();
    }

    private void iniciarJuego() {
        iniciarTablero();
        mostrarTablero();
        do {
            ejecutarTurno('X', EstadoJuego.USUARIO_GANA);
            if (estadoJuego == EstadoJuego.EN_CURSO) {
                ejecutarTurno('O', EstadoJuego.ORDENADOR_GANA);
            }
        } while (estadoJuego == EstadoJuego.EN_CURSO);
        mostrarResultado();
        scanner.close();
    }

    // 👌
    private void iniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    // 👌
    private void mostrarTablero() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tablero[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private void turnoUsuario() {
        int fila, columna;
        boolean posicionValida;
        do {
            System.out.println("Tu turno ingresa la fila y la columna (1-3): ");
            fila = scanner.nextInt() - 1;
            columna = scanner.nextInt() - 1;
            posicionValida = esMovimientoValido(fila, columna);
            if (!posicionValida) {
                System.out.println("Esta posición no es valida intenta nuevamente");
            }
        } while (!posicionValida);
        tablero[fila][columna] = 'X';
    }

    private boolean esMovimientoValido(int fila, int columna) {
        return fila >= 0 && fila < 3 && columna >= 0 && columna < 3 && tablero[fila][columna] == ' ';
    }

    private void turnoOrdenador() {
        int fila, columna;
        do {
            fila = (int) (Math.random() * 3);
            columna = (int) (Math.random() * 3);
        } while (tablero[fila][columna] != ' ');
        tablero[fila][columna] = 'O';
        System.out.println("El ordenador jugó en posición: " + (fila + 1) + ", " + (columna + 1));
    }

    private boolean comprobarGanador(char simbolo) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == simbolo && tablero[i][1] == simbolo && tablero[i][2] == simbolo) {
                return true;
            }
            if (tablero[0][i] == simbolo && tablero[1][i] == simbolo && tablero[2][i] == simbolo) {
                return true;
            }
        }
        if (tablero[0][0] == simbolo && tablero[1][1] == simbolo && tablero[2][2] == simbolo) {
            return true;
        }
        if (tablero[0][2] == simbolo && tablero[1][1] == simbolo && tablero[2][0] == simbolo) {
            return true;
        }
        return false;
    }

    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void mostrarResultado() {
        if (estadoJuego == EstadoJuego.USUARIO_GANA) {
            System.out.println("Felicidades Ganaste!!!");
        } else if (estadoJuego == EstadoJuego.ORDENADOR_GANA) {
            System.out.println("Ordenador Gana!!!");
        } else {
            System.out.println("EMPATE!!!");
        }
        scanner.close();
    }

    private void ejecutarTurno(char simbolo, EstadoJuego estadoGanador) {
        if (simbolo == 'X') {
            turnoUsuario();
        } else {
            turnoOrdenador();
        }
        mostrarTablero();
        if (comprobarGanador(simbolo)) {
            estadoJuego = estadoGanador;
        } else if (tableroLleno()) {
            estadoJuego = EstadoJuego.EMPATE;
        }
    }

    private enum EstadoJuego {
        EN_CURSO,
        USUARIO_GANA,
        ORDENADOR_GANA,
        EMPATE
    }
}
