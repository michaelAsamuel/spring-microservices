package com.empiricism.lnd.productservice.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor    // will create constructor
@Slf4j
public class ProductService {

    // Injected using constructor injection
    private final ProductRepository productRepository;
     public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved :", product.getId());
    }

    public List<ProductResponse> getAllProducts(){
         List<Product> products = productRepository.findAll();
         return products.stream().map(this::mapToProductResponse)
                 .toList();

    }

    private ProductResponse mapToProductResponse(Product product){
         return ProductResponse.builder()
                 .id(product.getId())
                 .name(product.getName())
                 .description(product.getDescription())
                 .price(product.getPrice())
                 .build();
    }
}
