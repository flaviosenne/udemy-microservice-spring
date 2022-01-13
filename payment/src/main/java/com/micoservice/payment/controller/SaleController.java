package com.micoservice.payment.controller;

import com.micoservice.payment.data.vo.SaleVO;
import com.micoservice.payment.service.SaleService;
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
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;
    private final PagedResourcesAssembler<SaleVO> assembler;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public SaleVO findById(@PathVariable("id") Long id){
        SaleVO sale = service.findById(id);
        sale.add(linkTo(methodOn(SaleController.class).findById(id)).withSelfRel());
        return sale;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "date"));

        Page<SaleVO> sales = service.findAll(pageable);
        sales.stream().forEach(p -> p.add(linkTo(methodOn(SaleController.class)
                .findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<SaleVO>> pagedModel =  assembler.toModel(sales);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }


    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public SaleVO create(@RequestBody SaleVO saleVO){
        SaleVO sale = service.create(saleVO);
        sale.add(linkTo(methodOn(SaleController.class).findById(sale.getId())).withSelfRel());
        return sale;
    }

}
