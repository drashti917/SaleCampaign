package com.example.demo.schedular.service;

import com.example.demo.schedular.model.Campaign;
import com.example.demo.schedular.model.CampaignDiscount;
import com.example.demo.schedular.model.PriceHistory;
import com.example.demo.schedular.model.Product;
import com.example.demo.schedular.repository.CampaignRepository;
import com.example.demo.schedular.repository.PriceHistoryRepository;
import com.example.demo.schedular.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
@Service
public class CampaignService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    ProductRepository repository;
    @Autowired
    PriceHistoryRepository priceHistoryRepository;

public Campaign save(Campaign campaign) {
    for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
        if (discount.getProduct() != null) {
            Product product = repository.findById(discount.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            product.setCurrentPrice(product.getMrp());
            discount.setProduct(product);
        }
        discount.setCampaign(campaign);
    }
    return campaignRepository.save(campaign);
}

    @Scheduled(cron = "0 10 18 * * ?")
    public void applyDiscount() {
        LocalDate currentDate = LocalDate.now();
        List<Campaign> activeCampaigns = campaignRepository.findActiveCampaigns(currentDate);

        if (activeCampaigns.isEmpty()) {
            System.out.println("No active campaigns for date: " + currentDate);
            return;
        }

        for (Campaign campaign : activeCampaigns) {
//            System.out.println("Processing Campaign: " + campaign.getTitle());
            for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
                Product product = discount.getProduct();
                double originalPrice = product.getCurrentPrice();
                double discountAmount = originalPrice * (discount.getDiscount() / 100.0);
                double newPrice = originalPrice - discountAmount;

//                System.out.println("Applying discount on product: " + product.getId());
//                System.out.println("Original Price: " + originalPrice + ", Discount: " + discount.getDiscount() + "%, New Price: " + newPrice);

                // Update Product Price
                product.setCurrentPrice(newPrice);
                repository.save(product);

                // Save Price History for tracking old price
                PriceHistory priceHistory = new PriceHistory();
                priceHistory.setPrice(newPrice);
                priceHistory.setDate(currentDate);
                priceHistory.setProduct(product);
                priceHistoryRepository.save(priceHistory);
            }
        }
    }


    @Scheduled(cron = "0 11 18 * * ?")
    public void endDate() {
        LocalDate currentDate = LocalDate.now();
        List<Campaign> campaignsEndingToday = campaignRepository.findCampaignsEndingToday(currentDate);

        if (campaignsEndingToday.isEmpty()) { //jo koy campaign no male to
            System.out.println("No campaigns ending on: " + currentDate);
            return;
        }

        for (Campaign campaign : campaignsEndingToday) {
            for (CampaignDiscount discount : campaign.getCampaignDiscounts()) {
                Product product = discount.getProduct();

                List<PriceHistory> priceHistoryList = priceHistoryRepository.findByProductId(product.getId()); //product ni original price ni list
                if (!priceHistoryList.isEmpty()) {
                    double lastPrice = priceHistoryList.get(0).getPrice();
                    System.out.println("Restoring original price for product: " + product.getId());
                    System.out.println("Restored Price: " + lastPrice);

                    // Restore original price
                    product.setCurrentPrice(lastPrice);
                    repository.save(product);

                    // Save Price History for restored price
                    PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProduct(product);
                    priceHistory.setPrice(product.getCurrentPrice());
                    priceHistory.setDate(currentDate);
                    priceHistoryRepository.save(priceHistory);
                }
            }
        }
    }

}
