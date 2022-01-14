package com.microservice.crud.service;

import com.microservice.crud.data.vo.ProductVO;
import com.microservice.crud.entity.Product;
import com.microservice.crud.exception.ResourceNotFoundException;
import com.microservice.crud.message.ProductSendMessage;
import com.microservice.crud.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductSendMessage productSendMessage;

    public ProductVO create(ProductVO productVO){
        ProductVO product = ProductVO.create(repository.save(ProductVO.create(productVO)));
        this.productSendMessage.sendMessage(product);
        return product;
    }

    public Page<ProductVO> findAll(Pageable pageable){
        return repository.findAll(pageable).map(this::convertToProductVO);
    }

    public ProductVO findById(Long id){
        return ProductVO.create(repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Produto não encontrado")));
    }

    public ProductVO update(ProductVO productVO){
        final Optional<Product> existProduct = repository.findById(productVO.getId());

        if(existProduct.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        return ProductVO.create(repository.save(ProductVO.create(productVO)));
    }

    public void delete(Long id){
        Product entity = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Produto não encontrado"));

        repository.delete(entity);
    }


    private ProductVO convertToProductVO(Product  product){
        return ProductVO.create(product);
    }
}
