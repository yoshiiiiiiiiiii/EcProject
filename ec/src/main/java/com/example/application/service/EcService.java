package com.example.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.form.ProductForm;

@Service
public interface EcService {

	public List<String> selectAll();

	public void insert(ProductForm productForm);

	public void update(ProductForm productForm);

	public void delete(ProductForm productForm);

}
