package com.myproject.webshop.service;

import java.io.IOException;
import java.io.InputStream;
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
public class ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductRatingRepository productRatingRepository;
	
	@Autowired
	private PurchasedRepository purchasedRepository;
	
	public void save(MultipartFile file, String name, String desc, double price, String category) {

	    Product product = new Product();
	    ProductDetail productDetail = new ProductDetail();

	    String imageBase64 = null;

	    try {
	        if (file != null && !file.isEmpty()) {

	            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	            if (!fileName.contains("..")) {
	                imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
	            }
	        }

	    } catch (IOException e) {
	        System.err.println("Image processing failed: " + e.getMessage());
	    }

	    if (imageBase64 == null) {
	        imageBase64 = getDefaultImage();
	    }

	    product.setImage(imageBase64);

	    productDetail.setDescription(desc);
	    product.setName(name);
	    product.setPrice(price);
	    product.setCategory(category);
	    product.setProductDetail(productDetail);

	    productRepository.save(product);
	}

	public List<Product> findAll() {
		return productRepository.findAllByOrderByNameAsc();
	}

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

	public long countByCategory(String category) {
		return productRepository.countByCategory(category);
	}

	public void addToRating(ProductRating productRating) {
		productRatingRepository.save(productRating);
		
	}

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

	public Page<ProductRating> getRatings(int pageNo, int pageSize, int id) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return productRatingRepository.findAllByOrderByDateOfRatingDesc(id, pageable);
	}

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

	public void savePurchasedItem(PurchasedItem item) {
		purchasedRepository.save(item);
		
	}
	
	public List<PurchasedItem> todaysOrders() {
		return purchasedRepository.todaysOrders();
	}

	public List<PurchasedItem> thisMonthsOrders() {
		return purchasedRepository.thisMonthsOrders();
	}
	
	private String getDefaultImage() {
	    try (InputStream is = getClass().getResourceAsStream("/static/img/picture-not-available.jpg")) {
	        if (is == null) {
	            return ""; 
	        }
	        return Base64.getEncoder().encodeToString(is.readAllBytes());
	    } catch (IOException e) {
	        return "";
	    }
	}
}
