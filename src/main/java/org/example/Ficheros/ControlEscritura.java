package org.example.Ficheros;

import org.example.ControlJuego;
import org.example.Enums.Estado;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ControlEscritura {
    private final static String RUTA_CARPETA = "resources";
    private final static String RUTA_ARCHIVO = "resources/registro_lanzamientos.xml";
    private static ControlEscritura instance;
    Document doc;

    private ControlEscritura() {
        try {
            crearFichero();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ControlEscritura getInstance() {
        if (instance == null) {
            instance = new ControlEscritura();
        }
        return instance;
    }

    public void crearFichero() {
        Path path = Paths.get(RUTA_CARPETA);
        Path path2 = Paths.get(RUTA_ARCHIVO);

        if (Files.notExists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (Files.notExists(path2)) {
            try {
                Files.createFile(path2);

                escribirContenidoInicial();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void escribirContenidoInicial() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            Element lanzamientos = doc.createElement("Lanzamientos");
            doc.appendChild(lanzamientos);

            guardarCambios();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertarInfo(String lanzaNombre, String navenombre, Estado estadoLanzamiento, LocalDate fechaLanzamiento, int cont) {
        try {
            Element lanzamientos = doc.getDocumentElement();

            Element infoNueva = doc.createElement("lanzamiento");
            lanzamientos.appendChild(infoNueva);

            Element lanzadera = doc.createElement("lanzadera");
            lanzadera.setTextContent(lanzaNombre);
            infoNueva.appendChild(lanzadera);

            Element nave = doc.createElement("nave");
            nave.setTextContent(navenombre);
            infoNueva.appendChild(nave);

            Element estado = doc.createElement("estado");
            estado.setTextContent(estadoLanzamiento.toString());
            infoNueva.appendChild(estado);

            if (estadoLanzamiento == Estado.APLAZADO) {
                Element fecha = doc.createElement("fecha");
                fecha.setTextContent(fechaLanzamiento.toString());
                infoNueva.appendChild(fecha);

                Element fechaNueva = doc.createElement("fecha_nueva");
                fechaNueva.setTextContent(fechaLanzamiento.plusMonths(1).plusDays(cont).toString());
                infoNueva.appendChild(fechaNueva);
            } else {
                Element fecha = doc.createElement("fecha");
                fecha.setTextContent(fechaLanzamiento.toString());
                infoNueva.appendChild(fecha);
            }

            guardarCambios();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void guardarCambios() {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer t = tFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(RUTA_ARCHIVO));

            t.transform(source, result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

