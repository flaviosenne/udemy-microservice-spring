package com.microservice.crud.service;

import com.microservice.crud.data.vo.ProductVO;
import com.microservice.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public ProductVO create(ProductVO productVO){
        return null;
    }
}
