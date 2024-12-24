/*
 * EJERCICIO:
 * El nuevo año está a punto de comenzar...
 * ¡Voy a ayudarte a planificar tus propósitos de nuevo año!
 *
 * Programa un gestor de objetivos con las siguientes características:
 * - Permite añadir objetivos (máximo 10)
 * - Calcular el plan detallado
 * - Guardar la planificación
 * 
 * Cada entrada de un objetivo está formado por (con un ejemplo):
 * - Meta: Leer libros
 * - Cantidad: 12
 * - Unidades: libros
 * - Plazo (en meses): 12 (máximo 12)
 *
 * El cálculo del plan detallado generará la siguiente salida:
 * - Un apartado para cada mes
 * - Un listado de objetivos calculados a cumplir en cada mes
 *   (ejemplo: si quiero leer 12 libros, dará como resultado 
 *   uno al mes)
 * - Cada objetivo debe poseer su nombre, la cantidad de
 *   unidades a completar en cada mes y su total. Por ejemplo:
 *
 *   Enero:
 *   [ ] 1. Leer libros (1 libro/mes). Total: 12.
 *   [ ] 2. Estudiar Git (1 curso/mes). Total: 1.
 *   Febrero:
 *   [ ] 1. Leer libros (1 libro/mes). Total: 12.
 *   ...
 *   Diciembre:
 *   [ ] 1. Leer libros (1 libro/mes). Total: 12.
 *
 * - Si la duración es menor a un año, finalizará en el mes
 *   correspondiente.
 *   
 * Por último, el cálculo detallado debe poder exportarse a .txt
 * (No subir el fichero)
 */import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Objetivo {
    private String meta;
    private int cantidad;
    private String unidades;
    private int plazo;
    private int cantidadPorMes;

    public Objetivo(String meta, int cantidad, String unidades, int plazo) {
        this.meta = meta;
        this.cantidad = cantidad;
        this.unidades = unidades;
        this.plazo = plazo;
        this.cantidadPorMes = cantidad / plazo;
    }

    public String getPlanDetalladoPorMes() {
        return String.format("[ ] %s (%d %s/mes). Total: %d.", 
            meta, cantidadPorMes, unidades, cantidad);
    }

    @Override
    public String toString() {
        return getPlanDetalladoPorMes();
    }
    // Getters y setters
    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }
    
    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public int getCantidadPorMes() {
        return cantidadPorMes;
    }
    // Otros métodos
    public void incrementarCantidad(int cantidad) {
        this.cantidad += cantidad;
        this.cantidadPorMes = this.cantidad / plazo;
    }

    public void incrementarPlazo(int plazo) {
        this.plazo += plazo;
        this.cantidadPorMes = cantidad / this.plazo;
    }



}

class Planificacion {
    private Objetivo[] objetivos = new Objetivo[10];
    private int contador = 0;

    public void addObjetivo(Objetivo objetivo) {
        if (contador < 10) {
            objetivos[contador] = objetivo;
            contador++;
        } else {
            System.out.println("No se pueden agregar más objetivos (máximo 10).");
        }
    }

    public String calcularPlanDetallado() {
        StringBuilder planDetallado = new StringBuilder();
        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (int mes = 0; mes < 12; mes++) {
            planDetallado.append(meses[mes]).append(":\n");
            for (Objetivo objetivo : objetivos) {
                if (objetivo != null && objetivo.getPlazo() > mes) {
                    planDetallado.append("    ").append(objetivo.getPlanDetalladoPorMes()).append("\n");
                }
            }
        }

        return planDetallado.toString();
    }

    public void guardarPlanificacion(String nombreArchivo) {
        String planDetallado = calcularPlanDetallado();
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(planDetallado);
            System.out.println("Planificación guardada en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar la planificación: " + e.getMessage());
        }
    }
}

public class MohamedElderkaoui {
    private static Planificacion planificacion = new Planificacion();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       do {
        System.out.println("1. Añadir objetivo");   
        System.out.println("2. Calcular plan detallado");
        System.out.println("3. Guardar planificación");
        System.out.println("4. Salir");
        int opcion = 0;
        do {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Inténtalo de nuevo.");
            } catch (Exception e) {
                // TODO: handle exception
            }
            
        } while (true);
        switch (opcion) {
            case 1:
            addObjetivo(scanner);
                break;
            case 2:
                System.out.println(planificacion.calcularPlanDetallado());
                break;
            case 3:
               savePlan(scanner);
                break;
            case 4:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida. Inténtalo de nuevo.");
                break;
        }
       } while (true);
    }

    private static void addObjetivo(Scanner scanner) {
        
        System.out.println("Meta:");
        String meta = System.console().readLine();
        System.out.println("Cantidad:");
        int cantidad = 0;
        do {
            try {
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    System.out.println("La cantidad debe ser mayor a 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida. Inténtalo de nuevo.");
            }
            

        } while (true);
        System.out.println("Unidades:");
        int unidades = 0;
        do {
            try {
                unidades = Integer.parseInt(scanner.nextLine());
                if (unidades <= 0) {
                    System.out.println("Las unidades deben ser mayor a 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Unidades inválidas. Inténtalo de nuevo.");
            }
            

        } while (true);
        System.out.println("Plazo (meses):");
        int plazo = 0;
        do {
            try {

                plazo = Integer.parseInt(scanner.nextLine());
                if (plazo <= 0) {
                    System.out.println("El plazo debe ser mayor a 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Plazo inválido. Inténtalo de nuevo.");
            }
            

        } while (true);
        Objetivo objetivo = new Objetivo(meta, cantidad, unidades+"", plazo);
        planificacion.addObjetivo(objetivo);
    }
    private static void savePlan(Scanner scanner) {
        System.out.println("Nombre del archivo:");
        String nombreArchivo = System.console().readLine();
        planificacion.guardarPlanificacion(nombreArchivo);
    }
}
