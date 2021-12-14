package util;


import model.Product;
import model.Products;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;


public class XMLReader {
    final static Logger logger = Logger.getLogger(PropertiesReader.class.getName());

    public List<Product> getXmlData() {
        File file;
        try {
            file = new File("src/main/resources/filters.xml");
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Unmarshaller un = context.createUnmarshaller();
            Products products = (Products) un.unmarshal(file);
            return products.getProducts();
        } catch (JAXBException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

}
