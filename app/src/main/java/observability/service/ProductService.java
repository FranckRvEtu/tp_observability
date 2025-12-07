package observability.service;


import observability.exceptions.ResourceNotFoundException;
import observability.model.Product;
import observability.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import observability.context.UserContext;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"READ\", \"method\": \"findAll\"}");;
        return productRepository.findAll();
    }

    public Product findById(String id) {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"READ\", \"method\": \"findById\"}");;
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));
    }

    public Product insert(Product product) {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"WRITE\", \"method\": \"insert\"}");;
        return productRepository.insert(product);
    }

    public void delete(String id) {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"WRITE\", \"method\": \"delete\"}");;
        // Pour lever une la même exception si l'objet n'existe pas
        findById(id);
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"WRITE\", \"method\": \"update\"}");;
        // Pour lever une la même exception si l'objet n'existe pas
        findById(product.getId());
        return productRepository.save(product);
    }

    public Product findMostExpensiveProduct() {
        LoggerFactory.getLogger("profile.logger").info("{\"timestamp\": \"" + java.time.Instant.now() + "\", \"user\": \"" +UserContext.getCurrentUser() + "\", \"action\": \"EXPENSIVE\", \"method\": \"findMostExpensiveProduct\"}");;
        Optional<Product> target = productRepository.findFirstByOrderByPriceDesc();
        if (target.isPresent()) {
            return target.get();
        } else {
            throw new ResourceNotFoundException("There is no such product");
        }
    }
}
