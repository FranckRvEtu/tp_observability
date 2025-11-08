package observability.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private float price;
    private Date date;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
