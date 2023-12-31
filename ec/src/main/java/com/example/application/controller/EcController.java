package com.example.application.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.application.form.LoginForm;
import com.example.application.form.ProductForm;
import com.example.application.serviceImpl.EcServiceImpl;

/*
 * コントローラークラス：アプリケーション層に位置し、serviceの呼び出しや、htmlにデータを受け渡す役割を持つ
 */
@Controller
public class EcController {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private EcServiceImpl ecService;

	@ModelAttribute
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		LoginForm loginForm = new LoginForm();
		loginForm.setId("");
		loginForm.setName("");
		model.addAttribute("loginForm", loginForm);
		model.addAttribute("message", "管理者情報を入力してください");

		return "login";
	}

	@ModelAttribute
	@RequestMapping(value = "/login_result", method = RequestMethod.POST)
	public String login_result(LoginForm loginForm, Model model) {
		List<Map<String, Object>> list;
		list = jdbcTemplate.queryForList("select * from user");

		for (int i = 0; i < list.size(); i++) {

			try {
				
				MessageDigest sha256;
				sha256 = MessageDigest.getInstance("SHA-256");
				byte[] sha256Result = sha256.digest(loginForm.getPassword().getBytes());
				String password = String.format("%040x", new BigInteger(1, sha256Result));
				if (("[" + loginForm.getId() + "," + " " + loginForm.getName() + "," + " " + password + "]")
						.compareTo((list.get(i).values().toString())) == 0) {
					model.addAttribute("message", "ログインに成功しました");
					break;
				}

				else {
					model.addAttribute("message", "ログインに失敗しました");
				}
				
			} catch (NoSuchAlgorithmException e) {
				
				model.addAttribute("message", "ログインに失敗しました(パスワードかIDに誤りがあります。)");
			}

		}

		return "login_result";
	}

	@ModelAttribute
	@RequestMapping(value = "/admin_menu", method = RequestMethod.GET)
	public String admin_menu(Model model) {
		model.addAttribute("message", "メニューを選択してください");

		return "admin_menu";
	}

	@ModelAttribute
	@RequestMapping(value = "/show_product", method = RequestMethod.GET)
	public String show_product(Model model) {
		List<String> products = null;
		products = ecService.selectAll();
		model.addAttribute("products", products);
		model.addAttribute("message", "商品を表示しました");

		return "show_product";
	}

	@ModelAttribute
	@RequestMapping(value = "/register_product", method = RequestMethod.GET)
	public String register_product(Model model) {
		ProductForm productForm = new ProductForm();
		model.addAttribute("productForm", productForm);
		model.addAttribute("message", "商品を登録してください");

		return "register_product";
	}

	@ModelAttribute
	@RequestMapping(value = "/update_product", method = RequestMethod.GET)
	public String update_product(Model model) {
		ProductForm productForm = new ProductForm();
		model.addAttribute("productForm", productForm);
		model.addAttribute("message", "商品を更新してください");

		return "update_product";
	}

	@ModelAttribute
	@RequestMapping(value = "/delete_product", method = RequestMethod.GET)
	public String delete_product(Model model) {
		ProductForm productForm = new ProductForm();
		model.addAttribute("productForm", productForm);
		model.addAttribute("message", "商品を削除してください");

		return "delete_product";
	}

	@ModelAttribute
	@RequestMapping(value = "/afeter_delete_product", method = RequestMethod.POST)
	public String afeter_delete_product(ProductForm productForm, Model model) {
		ecService.delete(productForm);
		model.addAttribute("message", "処理が完了しました");

		return "afeter_delete_product";
	}

	@ModelAttribute
	@RequestMapping(value = "/show_result", method = RequestMethod.POST)
	public String show_result(ProductForm productForm, Model model) {
		ecService.insert(productForm);
		model.addAttribute("message", "処理が完了しました");

		return "show_result";
	}
}