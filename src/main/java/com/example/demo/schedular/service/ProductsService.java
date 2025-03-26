package com.example.demo.schedular.service;

import com.example.demo.schedular.model.PriceHistory;
import com.example.demo.schedular.model.Product;
import com.example.demo.schedular.repository.PriceHistoryRepository;
import com.example.demo.schedular.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductsService {
    @Autowired
    ProductRepository repository;
    @Autowired
    PriceHistoryRepository priceHistoryRepository;

    public Product save(Product product){
        Product saveProduct = repository.save(product);

        PriceHistory priceHistory=new PriceHistory();
        priceHistory.setProduct(saveProduct);
        priceHistory.setDate(LocalDate.now());
        priceHistory.setPrice(product.getCurrentPrice());
        priceHistoryRepository.save(priceHistory);
        return saveProduct;
    }

    public Page<Product> getallproduct(int pageNo, int pageSize) {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            return repository.findAll(pageable);
    }

}
