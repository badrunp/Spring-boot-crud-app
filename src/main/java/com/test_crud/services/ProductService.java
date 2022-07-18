package com.test_crud.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test_crud.entity.ProductEntity;
import com.test_crud.repositories.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public Iterable<ProductEntity> getAllProduct(){
        return  productRepository.findAll();
    }

    public ProductEntity save(ProductEntity productEntity){
        return productRepository.save(productEntity);
    }

    public ProductEntity getProductById(Long id){
        return productRepository.findById(id).get();
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
}
