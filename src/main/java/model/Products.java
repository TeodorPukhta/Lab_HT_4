package model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {

    @XmlElement(name="product")
    private List<Product> products = null;

    public List<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
