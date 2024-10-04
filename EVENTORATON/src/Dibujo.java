import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dibujo {
    private Trazo cabeza;

    public void agregar(Trazo n) {
        if (n != null) {
            if (cabeza == null) {
                cabeza = n;
            } else {
                Trazo apuntador = cabeza;
                while (apuntador.siguiente != null) {
                    apuntador = apuntador.siguiente;
                }
                apuntador.siguiente = n;
            }
        }
    }

    public void eliminar(int x, int y) {
        if (cabeza == null) {
            return;
        }

        if (cabeza.contiene(x, y)) {
            cabeza = cabeza.siguiente;
            return;
        }

        Trazo anterior = cabeza;
        Trazo actual = cabeza.siguiente;

        while (actual != null) {
            if (actual.contiene(x, y)) {
                anterior.siguiente = actual.siguiente;
                break;
            }
            anterior = actual;
            actual = anterior.siguiente;
        }
    }

    public String[] aArchivo() {
        List<String> lineas = new ArrayList<>();
        Trazo apuntador = cabeza;
        while (apuntador != null) {
            lineas.add(apuntador.toString());
            apuntador = apuntador.siguiente;
        }
        return lineas.toArray(new String[0]);
    }

    public void desdeArchivo(String nombreArchivo) {
        try (BufferedReader br = Archivo.abrirArchivo(nombreArchivo)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    String[] datos = linea.split(",");
                    
                    // Verificar si la cantidad de datos es correcta
                    if (datos.length < 5) {
                        System.out.println("Formato incorrecto: " + linea);
                        continue; // Saltar a la siguiente línea
                    }
    
                    // Convertir el primer dato al enum Tipotrazo, manejar la excepción si no es válido
                    Tipotrazo tipoTrazo;
                    try {
                        tipoTrazo = Tipotrazo.valueOf(datos[0].toUpperCase()); // Convertir a mayúsculas para evitar errores de caso
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de trazo no válido: " + datos[0]);
                        continue; // Saltar a la siguiente línea si el tipo de trazo no es válido
                    }
    
                    // Parsear las coordenadas
                    int x1 = Integer.parseInt(datos[1]);
                    int y1 = Integer.parseInt(datos[2]);
                    int x2 = Integer.parseInt(datos[3]);
                    int y2 = Integer.parseInt(datos[4]);
    
                    // Crear y agregar el nuevo trazo
                    Trazo nuevoTrazo = new Trazo(tipoTrazo, x1, y1, x2, y2);
                    agregar(nuevoTrazo);
    
                } catch (NumberFormatException e) {
                    System.out.println("Error en el formato numérico: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dibujar(Graphics g) {
        Trazo apuntador = cabeza;
        while (apuntador != null) {
            apuntador.dibujar(g);
            apuntador = apuntador.siguiente;
        }
    }

    public Trazo[] trazos() {
        List<Trazo> listaTrazos = new ArrayList<>();
        Trazo apuntador = cabeza;
        while (apuntador != null) {
            listaTrazos.add(apuntador);
            apuntador = apuntador.siguiente;
        }
        return listaTrazos.toArray(new Trazo[0]);
    }
}


