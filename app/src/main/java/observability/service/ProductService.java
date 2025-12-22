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
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Produit non trouvé avec l'id : "+id)
        );
    }

    public Product insert(Product product) {
        return productRepository.insert(product);
    }

    public void delete(String id) {
        //Pour lever une la même exception si l'objet n'existe pas
        findById(id);
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        //Pour lever une la même exception si l'objet n'existe pas
        findById(product.getId());
        return productRepository.save(product);
    }

    public Product findMostExpensiveProduct() {
        Optional<Product> target = productRepository.findFirstByOrderByPriceDesc();
        if (target.isPresent()) {
            return target.get();
        }else{
            throw new ResourceNotFoundException("There is no such product");
        }
    }
}
