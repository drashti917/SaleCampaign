package com.example.demo.schedular.controller;

import com.example.demo.schedular.model.PaginationDto;
import com.example.demo.schedular.model.Product;
import com.example.demo.schedular.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtsss")
public class ProductsController {
    @Autowired
    ProductsService service;

    @PostMapping("save")
    public Product save(@RequestBody Product product){
        return service.save(product);
    }
    @GetMapping("pagination")
    public PaginationDto getallProducts(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page<Product> productsPage = service.getallproduct(pageNo, pageSize);
        return new PaginationDto(productsPage.getContent(),
                pageNo,pageSize,productsPage.getTotalPages());
    }
}
