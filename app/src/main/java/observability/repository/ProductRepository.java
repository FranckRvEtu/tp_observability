package observability.repository;

import observability.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findById(String id);
    Optional<Product> findTopByPriceDesc();
}
