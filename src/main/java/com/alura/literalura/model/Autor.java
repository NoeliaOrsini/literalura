package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true)

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("birth_year")
    private Integer anioDeNacimiento;

    @JsonProperty("death_year")
    private Integer anioDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    // Constructor vacío
    public Autor() {}

    // Constructor que recibe un DatosAutor (record)
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anioDeNacimiento = datosAutor.anioDeNacimiento();
        this.anioDeFallecimiento = datosAutor.anioDeFallecimiento();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibro(Libro libro) {
        this.libros.add(libro);
        libro.setAutor(this);
        addLibro(libro); // Llama al método addLibro para agregar el libro a la lista
    }

    private void addLibro(Libro libro) {
        this.libros.add(libro);
        libro.setAutor(this);
    }

    @Override
    public String toString() {
        StringBuilder librosString = new StringBuilder();
        for (int i = 0; i < libros.size(); i++) {
            librosString.append(libros.get(i).getTitulo());
            if (i < libros.size() - 1) {
                librosString.append(", ");
            }
        }
        return "Autor: " + nombre + "\n" +
                "Año de nacimiento: " + anioDeNacimiento + "\n" +
                "Año de muerte: " + anioDeFallecimiento + "\n" +
                "Libros: " + librosString.toString() + "\n";
    }


}