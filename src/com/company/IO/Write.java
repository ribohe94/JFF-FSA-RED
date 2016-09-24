package com.company.IO;

import com.company.Model.State;
import com.company.Model.Transition;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by ribohe94 on 17/09/16.
 *
 * Clase dedicada a "escribir" los archivos JFF
 *
 */
public class Write {

    public Write() {

    }

    /**
     * Escribe el docuemnto JFF
     *
     * @param doc  - Documento XML
     * @param path - Output path
     * @throws TransformerException
     */
    public void writeXML(Document doc, String path) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }

    /**
     * Crea un documento XML basandose en los argumentos que recibe
     * @param states - Lista de estados
     * @param transitions - Lista de transiciones
     * @param IDInitial - Nodo inicial
     * @return
     * @throws ParserConfigurationException
     */
    public Document makeDocument(ArrayList<State> states, ArrayList<Transition> transitions, String IDInitial) throws ParserConfigurationException {
        //Inicializamos el documento de salida
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        //Agregamos elementos structure, type, automaton
        Element structure = doc.createElement("structure");
        doc.appendChild(structure);

        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode("fa"));
        structure.appendChild(type);

        Element automaton = doc.createElement("automaton");
        structure.appendChild(automaton);

        //Agregamos los estados al documento
        Element stateNode;
        for (State state : states) {
            stateNode = doc.createElement("state");
            automaton.appendChild(stateNode);

            Attr attrID = doc.createAttribute("id");
            attrID.setValue(String.valueOf(state.getId()));

            Attr attrName = doc.createAttribute("name");
            attrName.setValue(state.getName());

            Element posX = doc.createElement("x");
            posX.appendChild(doc.createTextNode(String.valueOf(state.getX())));

            Element posY = doc.createElement("y");
            posY.appendChild(doc.createTextNode(String.valueOf(state.getY())));

            stateNode.setAttributeNode(attrID);
            stateNode.setAttributeNode(attrName);

            stateNode.appendChild(posX);
            stateNode.appendChild(posY);

            if(IDInitial.equals((String.valueOf(state.getId())))){
                Element initial = doc.createElement("initial");
                stateNode.appendChild(initial);
            }

            if (state.isFinal_state()) {
                Element nodeFinal = doc.createElement("final");
                stateNode.appendChild(nodeFinal);
            }
        }

        //Agregamos las transiciones al documento
        Element transitionNode;
        for(Transition transition : transitions) {
            transitionNode = doc.createElement("transition");
            automaton.appendChild(transitionNode);

            Element from = doc.createElement("from");
            from.appendChild(doc.createTextNode(String.valueOf(transition.getFrom())));

            Element to = doc.createElement("to");
            to.appendChild(doc.createTextNode(String.valueOf(transition.getTo())));

            Element value = doc.createElement("read");
            value.appendChild(doc.createTextNode(String.valueOf(transition.getValue())));

            transitionNode.appendChild(from);
            transitionNode.appendChild(to);
            transitionNode.appendChild(value);
        }

        doc.normalizeDocument();
        return doc;
    }

}
