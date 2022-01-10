package com.microservice.crud.service;

import com.microservice.crud.data.vo.ProductVO;
import com.microservice.crud.entity.Product;
import com.microservice.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public ProductVO create(ProductVO productVO){
        return ProductVO.create(repository.save(ProductVO.create(productVO)));
    }

    public Page<ProductVO> findAll(Pageable pageable){
        return repository.findAll(pageable).map(this::convertToProductVO);
    }

    public ProductVO findById(Long id){
        return ProductVO.create(repository.findById(id).orElseThrow(()->
                new ResolutionException("Produto não encontrado")));
    }

    public ProductVO update(ProductVO productVO){
        final Optional<Product> existProduct = repository.findById(productVO.getId());

        if(existProduct.isEmpty()){
            throw new ResolutionException("Produto não encontrado");
        }

        return ProductVO.create(repository.save(ProductVO.create(productVO)));
    }

    public void delete(Long id){
        Product entity = repository.findById(id).orElseThrow(() ->
                new ResolutionException("Produto não encontrado"));

        repository.delete(entity);
    }


    private ProductVO convertToProductVO(Product  product){
        return ProductVO.create(product);
    }
}
