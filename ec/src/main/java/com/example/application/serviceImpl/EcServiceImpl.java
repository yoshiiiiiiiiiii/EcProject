package com.example.application.serviceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.application.form.ProductForm;
import com.example.application.service.EcService;

@Service
public class EcServiceImpl implements EcService {

	@Override
	public List<String> selectAll() {
		List<String> entities = null;
		entities = new ArrayList<String>();
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_test", "yoshinari",
					"yoshinari0403");
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from product");

			while (resultSet.next()) {
				String str = "No." + resultSet.getString("code") + "  商品名：" + resultSet.getString("name") + "  説明："
						+ resultSet.getString("description") + "  値段：" + resultSet.getString("price") + "  評価："
						+ resultSet.getString("evaluation");
				entities.add(str);
			}

			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entities;
	}

	@Override
	public void insert(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_test", "yoshinari",
					"yoshinari0403");
			PreparedStatement statement = connection.prepareStatement("INSERT INTO product VALUES (?, ?, ?, ?, ?)");

			statement.setString(1, productForm.getCode());
			statement.setString(2, productForm.getName());
			statement.setString(3, productForm.getDescription());
			statement.setString(4, productForm.getPrice());
			statement.setString(5, productForm.getEvaluation());

			connection.setAutoCommit(true);
			statement.execute();

			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_test", "yoshinari",
					"yoshinari0403");
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE product SET code=?, name=?, description=?, price=?, evaluation=? WHERE code=?");

			statement.setString(1, productForm.getCode());
			statement.setString(2, productForm.getName());
			statement.setString(3, productForm.getDescription());
			statement.setString(4, productForm.getPrice());
			statement.setString(5, productForm.getEvaluation());
			statement.setString(6, productForm.getCode());

			connection.setAutoCommit(true);
			statement.execute();

			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_test", "yoshinari",
					"yoshinari0403");
			PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE code=?");

			statement.setString(1, productForm.getCode());

			connection.setAutoCommit(true);
			statement.execute();

			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
