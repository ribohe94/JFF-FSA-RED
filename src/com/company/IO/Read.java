package com.company.IO;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by ribohe94 on 17/09/16.
 */
public class Read {

    public Read() {

    }

    /**
     *
     * @param path - XML file path
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public Document getXMLFile(String path) throws IOException, SAXException, ParserConfigurationException {
        File file = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.normalizeDocument();
        return doc;
    }

}
