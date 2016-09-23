package com.company.IO;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * Created by Edwin on 22/09/2016.
 */

public class XMLValidator {
    public XMLValidator(){}

    public boolean validationXSD(String xsdFilePath, String xmlFilePath) {
        boolean isValid = validateXMLSchema(xsdFilePath,xmlFilePath);
        if(isValid){
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); // Define el namespace.
            Schema schema = factory.newSchema(new File(xsdPath)); // Instancia la referencia del esquema en memoria.
            Validator validator = schema.newValidator(); //Instancia el validador de un esquema.
            validator.validate(new StreamSource(new File(xmlPath))); //Hace la validación con la referencia del xml.
            return true;
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            return false;
        }catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            return false;
        }
    }

    //public void validationXMLDocument(){}
}