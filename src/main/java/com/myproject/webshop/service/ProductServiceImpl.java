package com.myproject.webshop.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.webshop.entity.Product;
import com.myproject.webshop.entity.ProductDetail;
import com.myproject.webshop.entity.ProductRating;
import com.myproject.webshop.entity.PurchasedItem;
import com.myproject.webshop.repository.ProductRatingRepository;
import com.myproject.webshop.repository.ProductRepository;
import com.myproject.webshop.repository.PurchasedRepository;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductRatingRepository productRatingRepository;
	
	@Autowired
	private PurchasedRepository purchasedRepository;
	
	public void save(MultipartFile file, String name, String desc, double price, String category) {
		Product product = new Product();
		ProductDetail productDetail = new ProductDetail();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		if(fileName.contains("..")) {
			System.out.println("Not a valid file");
		}
		
		try {
			product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		productDetail.setDescription(desc);
		product.setName(name);
		product.setPrice(price);
		product.setCategory(category);
		product.setProductDetail(productDetail);
		
		productRepository.save(product);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAllByOrderByNameAsc();
	}

	@Override
	public Product findById(int id) {
		Optional<Product> result = productRepository.findById(id);
		
		Product product = null;
		
		if(result.isPresent()) {
			product = result.get();
		}else {
			throw new RuntimeException("Did not find product id - " + id);
		}
		
		return product;
	}

	@Override
	public Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword, double min, double max, String category) {
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		
		double minPrice = min > 0 ? min : 0;
		double maxPrice = max >= min ? max : min;
		
		if(category.equals("") && keyword == null) {
			return productRepository.findAll(minPrice, maxPrice, pageable);
		}else if(category.equals("") && keyword != null) {
			return productRepository.findAllWithPrice(keyword, minPrice, maxPrice,  pageable);
		}else if(!(category.equals("")) && keyword == null) {
			return productRepository.findAll(minPrice, maxPrice, category, pageable);
		}else {
			return productRepository.findAllWithPrice(keyword, minPrice, maxPrice, category, pageable);
		}
	}

	@Override
	public long countByCategory(String category) {
		return productRepository.countByCategory(category);
	}

	@Override
	public void addToRating(ProductRating productRating) {
		productRatingRepository.save(productRating);
		
	}

	@Override
	@Transactional
	public void calculateAverage(Product product) {
		if(product.getProductRatings().size() == 0) {
			product.setRating(0.0);
		}else {
			int sum = 0;
			for(ProductRating pr : product.getProductRatings()) {
				sum += pr.getRating();
			}
			
			product.setRating(sum / (double) product.getProductRatings().size());
		}
		
		productRepository.calculateAverage(product.getId(), product.getRating());
	}

	@Override
	public Page<ProductRating> getRatings(int pageNo, int pageSize, int id) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRatingRepository.findAllByOrderByDateOfRatingDesc(id, pageable);
	}

	@Override
	public PurchasedItem getPurchasedItem(int id) {
		Optional<PurchasedItem> result = purchasedRepository.findById(id);
		
		PurchasedItem item = null;
		
		if(result.isPresent()) {
			item = result.get();
		}else {
			throw new RuntimeException("Did not find product id - " + id);
		}
		
		return item;
	}

	@Override
	public void savePurchasedItem(PurchasedItem item) {
		purchasedRepository.save(item);
		
	}
	
	@Override
	public List<PurchasedItem> todaysOrders() {
		return purchasedRepository.todaysOrders();
	}

	@Override
	public List<PurchasedItem> thisMonthsOrders() {
		return purchasedRepository.thisMonthsOrders();
	}
}
