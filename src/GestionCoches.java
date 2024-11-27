import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Clase principal GestionCoches
public class GestionCoches {
    private static final String FICHERO_COCHES = "coches.dat";
    private static final String FICHERO_CSV = "coches.csv";
    private static List<Coche> listaCoches = new ArrayList<>();

    // Desarrollo del Menú de la Aplicación
    public static void main(String[] args) {
        cargarDatos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    añadirCoche(scanner);
                    break;
                case 2:
                    borrarCoche(scanner);
                    break;
                case 3:
                    consultarCoche(scanner);
                    break;
                case 4:
                    listarCoches();
                    break;
                case 5:
                    guardarDatos();
                    System.out.println("Datos guardados. Programa terminado.");
                    break;
                case 6:
                    exportarCSV();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    // Menú de la Aplicación
    private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Añadir nuevo coche");
        System.out.println("2. Borrar coche por ID");
        System.out.println("3. Consultar coche por ID");
        System.out.println("4. Listado de coches");
        System.out.println("5. Terminar el programa");
        System.out.println("6. Exportar coches a archivo CSV");
        System.out.print("Selecciona una opción: ");
    }

    // Metodo para cargar datos
    private static void cargarDatos() {
        File archivo = new File(FICHERO_COCHES);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                listaCoches = (ArrayList<Coche>) ois.readObject();
                System.out.println("Datos cargados correctamente.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar los datos: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró el archivo. La lista está vacía.");
        }
    }

    // Metodo para guardar gatos
    private static void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_COCHES))) {
            oos.writeObject(listaCoches);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Metodo para añadir coches
    private static void añadirCoche(Scanner scanner) {
        System.out.print("Introduce el ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (listaCoches.stream().anyMatch(c -> c.getId() == id)) {
            System.out.println("Error: Ya existe un coche con este ID.");
            return;
        }

        System.out.print("Introduce la matrícula: ");
        String matricula = scanner.nextLine();
        if (listaCoches.stream().anyMatch(c -> c.getMatricula().equalsIgnoreCase(matricula))) {
            System.out.println("Error: Ya existe un coche con esta matrícula.");
            return;
        }

        System.out.print("Introduce la marca: ");
        String marca = scanner.nextLine();

        System.out.print("Introduce el modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Introduce el color: ");
        String color = scanner.nextLine();

        listaCoches.add(new Coche(id, matricula, marca, modelo, color));
        System.out.println("Coche añadido correctamente.");
    }

    // Metodo para borrar coches
    private static void borrarCoche(Scanner scanner) {
        System.out.print("Introduce el ID del coche a borrar: ");
        int id = scanner.nextInt();
        if (listaCoches.removeIf(c -> c.getId() == id)) {
            System.out.println("Coche eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún coche con ese ID.");
        }
    }

    // Metodo para consultar coches
    private static void consultarCoche(Scanner scanner) {
        System.out.print("Introduce el ID del coche a consultar: ");
        int id = scanner.nextInt();
        listaCoches.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("No se encontró ningún coche con ese ID.")
                );
    }

    // Metodo para sacar una lista de coches
    private static void listarCoches() {
        if (listaCoches.isEmpty()) {
            System.out.println("No hay coches en el almacén.");
        } else {
            listaCoches.forEach(System.out::println);
        }
    }

    // Metodo para exportar una lista de coches en CSV
    private static void exportarCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHERO_CSV))) {
            writer.println("ID;Matrícula;Marca;Modelo;Color");
            for (Coche coche : listaCoches) {
                writer.printf("%d;%s;%s;%s;%s%n",
                        coche.getId(), coche.getMatricula(), coche.getMarca(), coche.getModelo(), coche.getColor());
            }
            System.out.println("Coches exportados a archivo CSV correctamente.");
        } catch (IOException e) {
            System.err.println("Error al exportar a CSV: " + e.getMessage());
        }
    }
}
