package com.myproject.webshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.webshop.entity.Product;
import com.myproject.webshop.entity.ProductRating;
import com.myproject.webshop.entity.PurchasedItem;

@Service
public interface ProductService {

	public void save(MultipartFile file, String name, String desc, double price, String category);

	public List<Product> findAll();

	public Product findById(int id);
	
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword, double min, double max, String category);

	public long countByCategory(String category);

	public void addToRating(ProductRating productRating);

	public void calculateAverage(Product product);

	public Page<ProductRating> getRatings(int pageNo, int pageSize, int id);

	public PurchasedItem getPurchasedItem(int id);

	public void savePurchasedItem(PurchasedItem item);
	
	public List<PurchasedItem> todaysOrders();

	public List<PurchasedItem> thisMonthsOrders();

}
