package com.company.IO;

import com.company.Model.State;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by ribohe94 on 17/09/16.
 */
public class Write {

    public Write() {

    }

    /**
     *
     * @param doc - Documento XML
     * @param path - Output path
     * @throws TransformerException
     */
    public void writeXML (Document doc, String path) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }

    public Document makeDocument(ArrayList<State> states) throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element structure = doc.createElement("structure");
        doc.appendChild(structure);


        doc.normalizeDocument();
        return doc;
    }


}
