package com.microservice.crud.controller;

import com.microservice.crud.data.vo.ProductVO;
import com.microservice.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final PagedResourcesAssembler<ProductVO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ProductVO findById(@PathVariable("id") Long id){
        ProductVO product = service.findById(id);
        product.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return product;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
            ){
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

        Page<ProductVO> products = service.findAll(pageable);
        products.stream().forEach(p -> p.add(linkTo(methodOn(ProductController.class)
                .findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProductVO>> pagedModel =  assembler.toModel(products);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }


    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProductVO create(@RequestBody ProductVO productVO){
        ProductVO product = service.create(productVO);
        product.add(linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel());
        return product;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProductVO update(@RequestBody ProductVO productVO){
        ProductVO product = service.update(productVO);
        product.add(linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel());
        return product;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
