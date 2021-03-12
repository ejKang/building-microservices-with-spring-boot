package ecommerce.firstproduct.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.productservice.entity.Product;
import ecommerce.productservice.repository.ProductRepository;

@RestController
@RequestMapping("/first/product")
public class ProductFirstController {
    
    @Autowired
    private ProductRepository repo;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Value("${product.jms.destination}")
    private String jmsQueue;

     @PostMapping("/addOne")
     public Product addProduct(@RequestBody Product product	) {
    	 return repo.save(product);
     }
     
     @PostMapping("/addList")
     public List<Product> addProductList (@RequestBody List<Product> products) {
    	 return repo.saveAll(products);
     }
     
     @GetMapping("/getAll")
     public List<Product> getAllProduct() {
    	 return repo.findAll();
     }
     
     @GetMapping("/sendToCart/{id}")
     public ResponseEntity<Product> sendToCart(@PathVariable long id) {
    	 Optional<Product> product = repo.findById(id);
    	 
    	 if (!product.isPresent()) {
    		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	 }
    	 
    	 ObjectMapper mapper = new ObjectMapper();
    	 try {
			String jsonString = mapper.writeValueAsString(product.get());
			
			jmsTemplate.convertAndSend(jmsQueue, jsonString);
			return new ResponseEntity<>(product.get(), HttpStatus.OK);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
     }
    
}
