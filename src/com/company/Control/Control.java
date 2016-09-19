package com.company.Control;

import com.company.IO.Read;
import com.company.IO.Write;
import com.company.Model.State;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ribohe94 on 18/09/16.
 */
public class Control {

    public Control() {
        states = new ArrayList<>();
        read = new Read();
        write = new Write();
    }

    public ArrayList<State> makeStateList(String path) {
        Document doc = null;
        //Parseamos el docuemnto XML
        try {
            doc = read.getXMLFile(path);
            NodeList nlist = doc.getElementsByTagName("state");
            Node node;
            for (int i = 0; i < nlist.getLength(); i++) {
                node = nlist.item(i);
                Element element = (Element) node;
                String id = element.getAttribute("id");
                String name = element.getAttribute("name");
                float posX = Float.valueOf(element.getElementsByTagName("x").item(0).getTextContent());
                float posY = Float.valueOf(element.getElementsByTagName("y").item(0).getTextContent());
                boolean isFinal = false;
                if(element.getElementsByTagName("final").item(0) != null)
                    isFinal = true;
                states.add(new State(Integer.valueOf(id), name, posX, posY, isFinal));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return states;

    }



    private ArrayList<State> states;
    private Read read;
    private Write write;
}
