package com.example.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EcService {
	public List<String> selectAll() {
		List<String> entities = null;
		entities = new ArrayList<String>();
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?serverTimezone=JST", "root",
					"root");
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from product");

			while (resultSet.next()) {
				String str = resultSet.getString("code") + "  " + resultSet.getString("name") + "  "
						+ resultSet.getString("description") + "  " + resultSet.getString("price") + "  "
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

	public void insert(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?serverTimezone=JST", "root",
					"root");
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

	public void update(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?serverTimezone=JST", "root",
					"root");
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

	public void delete(ProductForm productForm) {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?serverTimezone=JST", "root",
					"root");
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
