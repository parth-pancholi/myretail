/**
 * 
 */
package com.codetest.app.myRetail.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codetest.app.myRetail.entity.Product;
import com.codetest.app.myRetail.exception.MyRetailException;
import com.codetest.app.myRetail.helper.ProductHelper;
import com.codetest.app.myRetail.remoteHttp.ConnectHttpClient;
import com.codetest.app.myRetail.repository.ProductRepository;
import com.codetest.app.myRetail.vo.response.ProductVO;

/**
 * @author rpcha
 *
 */
@Service
public class ProductService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ProductRepository productRepository;
		
	@Autowired
	ConnectHttpClient connectHttpClient;
	
	@Autowired
	ProductHelper helperObject;
	
	public ProductService() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductVO getProductById(String productId) throws MyRetailException {
		logger.info("Inside ProductService().getProductById");
		
		Product product = new Product();
		String productName=null;
		try {
		//retrive from MongoDB
		product = productRepository.findProductByproductId(productId);
		if(product == null) {
			logger.debug("Product Not Found Exception while fetching product data from DB ");
			throw new MyRetailException(HttpStatus.NOT_FOUND.value(),"Product not found in DB");
		}
		
		//Retrieve title from product API
		//TO-DO write method to call Redsky API to fetch the title
		 productName = connectHttpClient.getProductNameByRemoteCall(productId);
		}catch(MyRetailException e) {
			throw e; 
		}
		return helperObject.generateProductResponse(product,productName);

	}

	public void updateProductById(ProductVO productVO) throws MyRetailException{
		try {
		Product product =helperObject.getProductDomainObject(productVO);
		productRepository.save(product);
		} catch (Exception exception) {
			logger.debug("Product Not Found Exception while doing update " + exception);
			throw new MyRetailException(HttpStatus.NOT_FOUND.value(),"Product not found while update");
		}
	}

}
