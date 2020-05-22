package com.codetest.app.myRetail.helper;

import org.springframework.stereotype.Component;

import com.codetest.app.myRetail.entity.CurrentPrice;
import com.codetest.app.myRetail.entity.Product;
import com.codetest.app.myRetail.exception.MyRetailException;
import com.codetest.app.myRetail.vo.response.CurrentPriceVO;
import com.codetest.app.myRetail.vo.response.ProductVO;

@Component
public class ProductHelper {

	/**
	 * 
	 */
	public ProductHelper() {
		// TODO Auto-generated constructor stub
	}

	public ProductVO generateProductResponse(Product product, String productName) throws MyRetailException {


		ProductVO prodResponse = new ProductVO();
		CurrentPriceVO currentPriceResponse= new CurrentPriceVO();
		try{
			currentPriceResponse.setCurrencyCode(product.getCurrentPrice().getCurrencyCode());
			currentPriceResponse.setValue(product.getCurrentPrice().getValue());

			prodResponse.setProductId(product.getProductId());
			prodResponse.setCurrentprice(currentPriceResponse);
			prodResponse.setName(productName);
		}
		catch(Exception e) {
			throw new MyRetailException();
		}
		return prodResponse;
	}

	public Product getProductDomainObject(ProductVO productVO) {
		Product product = new Product();
		CurrentPrice currentPrice = new CurrentPrice();
		product.setProductId(productVO.getProductId());
		currentPrice.setCurrencyCode(productVO.getCurrentprice().getCurrencyCode());
		currentPrice.setValue(productVO.getCurrentprice().getValue());
		product.setCurrentPrice(currentPrice);
		return product;
	}



}
