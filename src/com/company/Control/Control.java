package com.company.Control;

import com.company.IO.Read;
import com.company.IO.Write;
import com.company.IO.XMLValidator;
import com.company.Model.State;
import com.company.Model.Transition;
import com.company.UI.Input;
import com.company.UI.States;
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
 *
 * Clase de control. Maneja toda la información y las demás clases para generar el automata deseado.
 *
 */
public class Control {

    /**
     * Contructor, inicializa las variables relevantes
     */
    public Control() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
        alphabet = new HashSet<>();
        read = new Read();
        write = new Write();
        XMLValidators = new XMLValidator();
    }

    /**
     *
     * Metodo inicial. Pone todo en marcha y corre los argumentos.
     *
     * @param input - Argumento ingresado desde la consola
     */
    public void init(String [] input) {
        com.company.UI.State s = States.Init;
        while (s != null) {
            s = s.next(new Input(input));
        }
    }

    /**
     *
     * Regresa una lista de estados basado en el archivo .jff
     *
     * @param path - Direccion del archivo de entrada
     * @return
     */
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

    /**
     *
     * Regresa una lista de transiciones basado en el archivo .jff
     *
     * @param path - Direccion del archivo de entrada
     * @return
     */
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

    /**
     *
     * Genera lalista de caracteres utilizados en el automata
     *
     * @param path  - Direccion del archivo de entrada
     * @return
     */
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

    /**
     * Regresa las posibles transiciones vacías (Lambda) desde los estados otorgados. Solo regresa un nivel, osea, solo lee a
     * un nodo de distancia.
     *
     * @param inStates - Lista de estados a evaluar
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
     * @param inStates - Lista de estados a evaluar
     * @param value - Valor de entrada
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
     * Corre el metodo getTransition recursivamente para abarcar todos los niveles de profundidad posibles.
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
     * Metodo principal de reduccion. Utiliza todos los metodos previos para reducir el automata.
     * finalmente lo imprime en formato .JFF en el directorio especificado
     * @param path - directorio de salida
     */
    public void reduce(String path) {
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
                    for (State state : key) {
                        if (state.isFinal_state()) {
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
            write.writeXML(doc, path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
        
	public boolean ValidateXMLFiles(String XMLPath){
		// Llama al validador e imprime el nombre del archivo junto con una O si la validación es correcta y X si no. 
        boolean val = XMLValidators.validationXSD(XSDPath,XMLPath);
        if(val){
            System.out.println(XMLPath + " XSD -> O.");
		} else {
            System.out.println(XMLPath + " XSD -> X.");
        }
        return val;
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
