package com.micoservice.payment.service;

import com.micoservice.payment.data.vo.ProductSaleVO;
import com.micoservice.payment.data.vo.SaleVO;
import com.micoservice.payment.entity.ProductSale;
import com.micoservice.payment.entity.Sale;
import com.micoservice.payment.exception.ResourceNotFoundException;
import com.micoservice.payment.repository.ProductRepository;
import com.micoservice.payment.repository.ProductSaleRepository;
import com.micoservice.payment.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository repository;
    private final ProductSaleRepository productSaleRepository;

    public SaleVO create(SaleVO saleVO){
        Sale saleSaved = this.repository.save(SaleVO.create(saleVO));

        List<ProductSale> productSaved = new ArrayList<>();
        saleVO.getProducts().forEach(product -> {
            ProductSale productSale = ProductSaleVO.create(product);
            productSale.setSale(saleSaved);
            productSaved.add(this.productSaleRepository.save(productSale));
        });
        saleSaved.setProducts(productSaved);

        return SaleVO.create(saleSaved);
    }

    public Page<SaleVO> findAll(Pageable pageable){
        return repository.findAll(pageable).map(this::convertToSaleVO);
    }

    public SaleVO findById(Long id){
        return SaleVO.create(repository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Venda não encontrada")));
    }

    private SaleVO convertToSaleVO(Sale product){
        return SaleVO.create(product);
    }
}
