import java.awt.*;

class Trazo {
    private Tipotrazo tipo;
    private int x1, y1, x2, y2;
    Trazo siguiente;

    public Trazo(Tipotrazo tipo, int x1, int y1, int x2, int y2) {
        this.tipo = tipo;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public void dibujar(Graphics g) {
        switch (tipo) {
            case LINEA:
                g.drawLine(x1, y1, x2, y2);
                break;
            case CUADRADO:
                g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                break;
            case CIRCULO:
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                break;
            case TRIANGULO:
                int[] xPoints = {x1, x2, (x1 + x2) / 2};
                int[] yPoints = {y2, y2, y1};
                g.drawPolygon(xPoints, yPoints, 3);
                break;
        }
    }

    public boolean contiene(int x, int y) {
        return (x >= Math.min(x1, x2) && x <= Math.max(x1, x2) &&
                y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
    }

    // Métodos get...
}
