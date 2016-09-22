package com.company.Control;

import com.company.IO.Read;
import com.company.IO.Write;
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
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ribohe94 on 18/09/16.
 */
public class Control {

    public Control() {
        states = new ArrayList<>();
        transitions = new ArrayList<>();
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
                boolean initial = false;
                if (element.getElementsByTagName("final").item(0) != null)
                    isFinal = true;
                if (element.getElementsByTagName("initial").item(0) != null)
                    initial = true;
                states.add(new State(Integer.valueOf(id), name, posX, posY, isFinal, initial));
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

    public void writeFSA(String path) {
        try {
            Document doc = write.makeDocument(states, transitions);
            write.writeXML(doc, path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transition> getFromTransitions(State inState) {
        ArrayList<Transition> fromTransitions = new ArrayList<>();
        for (Transition transition : transitions) {
            if (transition.getFrom() == inState.getId()) {
                fromTransitions.add(transition);
            }
        }
        return fromTransitions;
    }

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

    public HashSet<State> getRecursiveLockStates(State inState) {
        HashSet<State> recursiveStates = new HashSet<>();
        recursiveStates.add(inState);
        for (int i = 0; i < recursiveStates.size(); i++) {
            recursiveStates.addAll(getTransition(recursiveStates));
        }
        return recursiveStates;
    }


    public void reduce() {
        State inState = null;
        for (State state : states) {
            if (state.isInitial()) {
                inState = state;
            }
        }
        HashSet<State> recursiveStates = new HashSet<>();
        recursiveStates = getRecursiveLockStates(inState);


    }

    private ArrayList<State> states;
    private ArrayList<Transition> transitions;
    private HashSet<HashSet<State>> FIFO;
    private Read read;
    private Write write;
}
