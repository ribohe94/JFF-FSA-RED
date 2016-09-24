package com.company.Control;

import com.company.IO.Read;
import com.company.IO.Write;
import com.company.IO.XMLValidator;
import com.company.Model.State;
import com.company.Model.Transition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;

/**
 * Created by ribohe94 on 18/09/16.
 */
public class Control {

    public Control() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        alphabet = new HashSet<>();
        read = new Read();
        write = new Write();
//        FIFO = new HashSet<>();
        XMLValidators = new XMLValidator();
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
                if (element.getElementsByTagName("final").item(0) != null)
                    isFinal = true;
                if (element.getElementsByTagName("initial").item(0) != null)
                    IDInitial = id;
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

    public ArrayList<Transition> makeTransitionList(String path) {
        Document doc = null;
        //Parseamos el docuemnto XML
        try {
            doc = read.getXMLFile(path);
            NodeList nlist = doc.getElementsByTagName("transition");
            Node node;
            for (int i = 0; i < nlist.getLength(); i++) {
                node = nlist.item(i);
                Element element = (Element) node;
                int from = Integer.valueOf(element.getElementsByTagName("from").item(0).getTextContent());
                int to = Integer.valueOf(element.getElementsByTagName("to").item(0).getTextContent());
                String value = element.getElementsByTagName("read").item(0).getTextContent();
                transitions.add(new Transition(from, to, value));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return transitions;

    }

    public HashSet<String> makeAlphabet(String path) {
        Document doc = null;
        //Parseamos el docuemnto XML
        try {
            doc = read.getXMLFile(path);
            NodeList nlist = doc.getElementsByTagName("transition");
            Node node;
            for (int i = 0; i < nlist.getLength(); i++) {
                node = nlist.item(i);
                Element element = (Element) node;
                String value = element.getElementsByTagName("read").item(0).getTextContent();
                if (!"".equals(value))
                    alphabet.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return alphabet;
    }

    public void writeFSA(String path) {
        try {
            Document doc = write.makeDocument(states, transitions, IDInitial);
            write.writeXML(doc, path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void ValidateXMLFiles(String XMLPath){
		// Llama al validador e imprime el nombre del archivo junto con una O si la validación es correcta y X si no. 
        boolean val = XMLValidators.validationXSD(XSDPath,XMLPath);
        if(val){
            System.out.println(XMLPath+" XSD -> O.");
=======
    public ArrayList<Transition> getFromTransitions(State inState) {
        ArrayList<Transition> fromTransitions = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.getFrom() == inState.getId()) {
                fromTransitions.add(transition);
            }
>>>>>>> 165b114062c44a16b444b38c87c0dfc6eb2b6f04
        }
        return fromTransitions;
    }

    /**
     * Regresa las posibles transiciones vacías desde los estados otorgados. Solo regresa un nivel, osea, solo lee a
     * un nodo de distancia.
     *
     * @param inStates
     * @return
     */
    public HashSet<State> getTransition(HashSet<State> inStates) {
        HashSet<State> lambdaStates = new HashSet<>();
        for (State state : inStates) {
            for (Transition transition : transitions) {
                if (transition.getFrom() == state.getId() && transition.getValue().equals("")) {
                    lambdaStates.add(states.get(transition.getTo()));
                }
            }
        }
        return lambdaStates;
    }

    /**
     * Regresa las posibles transiciones con el valor otorgado
     *
     * @param inStates
     * @param value
     * @return
     */
    public HashSet<State> getTransition(HashSet<State> inStates, String value) {
        HashSet<State> transitionStates = new HashSet<>();
        for (State state : inStates) {
            for (Transition transition : transitions) {
                if (transition.getFrom() == state.getId() && transition.getValue().equals(value)) {
                    transitionStates.add(states.get(transition.getTo()));
                }
            }
        }
        return transitionStates;
    }

    /**
     * Regresa la cantidad completa de transiciones vacías posibles.
     *
     * @param inStates
     * @return
     */
    public HashSet<State> getRecursiveLockStates(HashSet<State> inStates) {
        HashSet<State> recursiveStates = new HashSet<>();
        for (State state : inStates) {
            recursiveStates.add(state);
            for (int i = 0; i < recursiveStates.size(); i++) {
                recursiveStates.addAll(getTransition(recursiveStates));
            }
        }
        return recursiveStates;
    }

    /**
     *
     */
    public void reduce() {
        HashSet<State> outStates = new HashSet<>();
        HashSet<Transition> outTransitions = new HashSet<>();
        Map<HashSet<State>, State> mappedStates = new HashMap<>();

        State inState = null;
        for (State state : states) {
            if (state.getId() == Integer.valueOf(IDInitial)) {
                inState = state;
            }
        }

        outStates.add(inState);
        HashSet<State> init = new HashSet<>();
        init.add(inState);

        mappedStates.put(getRecursiveLockStates(init), new State(0, "q0", 0, 0, inState.isFinal_state()));
        ArrayList<HashSet<State>> loopList = new ArrayList(mappedStates.keySet());
        int stateName = 0;
        for (int i = 0; i < loopList.size(); i++) {
            for (String value : alphabet) {
                if (!mappedStates.containsKey(getRecursiveLockStates(getTransition(loopList.get(i), value)))) {
                    HashSet<State> key = getRecursiveLockStates(getTransition(loopList.get(i), value));

                    //Verificamos si hay un estado final en la cerradura
                    boolean isFinal = false;
                    for(State state : key) {
                        if(state.isFinal_state()) {
                            isFinal = true;
                        }
                    }

                    State newState = new State(++stateName, "q" + String.valueOf(stateName), 0, 0, isFinal);
                    mappedStates.put(key, newState);
                    loopList.add(getRecursiveLockStates(getTransition(loopList.get(i), value)));
                    outStates.add(newState);
                    outTransitions.add(new Transition(mappedStates.get(loopList.get(i)).getId(), newState.getId(), value));
                } else {
                    HashSet<State> key = getRecursiveLockStates(getTransition(loopList.get(i), value));
                    outTransitions.add(new Transition(mappedStates.get(loopList.get(i)).getId(), mappedStates.get(key).getId(), value));

                }
            }
        }
        ArrayList<State> outStatesList = new ArrayList<>(outStates);
        ArrayList<Transition> outTransitionsList = new ArrayList<>(outTransitions);
        try {
            Document doc = write.makeDocument(outStatesList, outTransitionsList, String.valueOf(inState.getId()));
            write.writeXML(doc, "output.jff");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public void ValidateXMLFiles(String XMLPath) {
        boolean val = XMLValidators.validationXSD(XSDPath, XMLPath);
        if (val) {
            System.out.println(XMLPath + " XSD -> O.");
        } else {
            System.out.println(XMLPath + " XSD -> X.");
        }
    }

    private void DisperseStates(ArrayList<State> states){
        //Recibe los estados del autómata determinista (simplificado) y reasigna coordenadas (X,Y) lejanas entre si para cada estado.
        //Se debe llamar a este método antes de crear el archivo final.
        
    }

    private ArrayList<State> states;
    private ArrayList<Transition> transitions;
    private HashSet<String> alphabet;
    private ArrayList<HashSet<State>> FIFO;
    private Read read;
    private Write write;
    private String IDInitial;
    private XMLValidator XMLValidators;
    private static String XSDPath = "src/com/company/Model/Automatons.xsd";
}
