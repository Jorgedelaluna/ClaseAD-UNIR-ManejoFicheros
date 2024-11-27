import java.io.Serializable;

// Clase coche
public class Coche implements Serializable {
    private int id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;

    // Constructor de coche
    public Coche(int id, String matricula, String marca, String modelo, String color) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }

    // Métodos Getters y Setters
    public int getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }
    public Object getMarca() {
        return marca;
    }

    public Object getModelo() {
        return modelo;
    }

    public Object getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Matrícula: " + matricula + ", Marca: " + marca + ", Modelo: " + modelo + ", Color: " + color;
    }


}