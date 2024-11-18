package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import jakarta.transaction.Transactional;

import java.util.*;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
            ====================================================
                                LITERALURA
            ====================================================                    
            *** ESCRIBE EL NUMERO DE LA OPCION DESEADA ***
            
            1 - Buscar libro por título
            2 - Listado de libros registrados
            3 - Listado de autores registrados
            4 - Buscar autores vivos en un determinado año
            5 - Buscar libro por idioma
            0 - Salir
            
            ====================================================
            """;
            System.out.println(menu);

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consumir la línea restante

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        mostrarListadoLibros();
                        break;
                    case 3:
                        mostrarListadoAutores();
                        break;
                    case 4:
                        buscarAutoresVivos();
                        break;
                    case 5:
                        buscarLibroPorIdioma();
                        break;
                    case 0:
                        System.out.println("\nCERRANDO LA APLICACION ..");
                        System.out.println("\nGracias por utilizar nuestros servicios\n");
                        break;

                    default:
                        System.out.println("OPCION INVALIDA. Elija una opción válida del 1 al 5 o 0 para salir.");
                }
            } catch (InputMismatchException e) {
                System.out.println("OPCION INVALIDA. Elija una opción válida del 1 al 5 o 0 para salir.");
                teclado.nextLine(); // Limpiar el buffer en caso de entrada no numérica
            }
        }
    }


    private void buscarLibro() {
        System.out.println("\nINGRESE EL NOMBRE DEL LIBRO QUE DESEA CONSULTAR:");
        var tituloLibro = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream().findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\nLibro encontrado\n");

            DatosLibros datosLibro = libroBuscado.get();

            // Verifica si el libro ya existe en la base de datos
            Optional<Libro> libroExistente = libroRepositorio.findByTitulo(datosLibro.titulo());

            if (libroExistente.isPresent()) {
                System.out.println("\nESTE LIBRO YA SE ENCUENTRA REGISTRADO EN LA BASE DE DATOS\n");
            } else {
                Libro libro = new Libro(datosLibro);
                libroRepositorio.save(libro);

                System.out.println("--------- LIBRO -----------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idiomas: " + libro.getIdiomas());
                System.out.println("Número de Descargas: " + libro.getNumeroDeDescargas());
                System.out.println("---------------------------");
            }
        } else {
            System.out.println("\nLIBRO NO ENCONTRADO\n");
        }
    }

    @Transactional
    private void mostrarListadoLibros() { // imprimir con tostring
        libros = libroRepositorio.findAll(); // Inicialización de la variable libros
        System.out.println("\nLISTADO DE LIBROS REGISTRADOS:\n");
        libros.forEach(libro -> System.out.println(libro)); // Usando forEach y toString
    }


    @Transactional // imprimir con tostring
    private void mostrarListadoAutores() {
        autores = autorRepositorio.findAll();
        System.out.println("\nLISTADO DE AUTORES REGISTRADOS:\n");
        autores.forEach(autor -> System.out.println(autor)); // Usando forEach y toString
    }

    private void buscarAutoresVivos() {
        System.out.println("\nINGRESE EL AÑO PARA VERIFICAR LOS AUTORES VIVOS:");
        int año = teclado.nextInt();
        teclado.nextLine();

      List<Autor> autoresVivos = autorRepositorio.findAutoresVivosEnAño(año);
      System.out.println("\nAUTORES VIVOS EN EL AÑO " + año + ":");
        for (Autor autor : autoresVivos) {
            System.out.println(autor);
        }
    }

    private void buscarLibroPorIdioma() {
        Scanner teclado = new Scanner(System.in); // Asegúrate de que el Scanner esté definido

        // Mensaje inicial con opciones de idioma, cada una en una línea
        System.out.println("\nELIJA EL IDIOMA QUE DESEA BUSCAR LIBROS:");
        System.out.println("es - español");
        System.out.println("en - inglés");
        System.out.println("fr - francés");
        System.out.println("pt - portugués");

        String idioma;
        boolean idiomaValido = false;

        // Repetir hasta que el usuario ingrese una opción válida
        do {
            System.out.print("\nIngrese el código del idioma: ");
            idioma = teclado.nextLine().toLowerCase(); // Convertir a minúsculas

            // Verificar si el idioma ingresado es válido
            if (idioma.equals("es") || idioma.equals("en") || idioma.equals("fr") || idioma.equals("pt")) {
                idiomaValido = true; // Salir del bucle si el idioma es válido
            } else {
                System.out.println("OPCION INCORRECTA. Por favor, ingrese un código de idioma válido (es, en, fr, pt).");
            }
        } while (!idiomaValido);

        // Buscar libros por el idioma especificado
        List<Libro> librosPorIdioma = libroRepositorio.findByIdiomas(idioma);

        // Mostrar los resultados
        System.out.println("\nLibros en el idioma " + idioma + ":");
        for (Libro libro : librosPorIdioma) {
            System.out.println(libro);
        }
    }

}
