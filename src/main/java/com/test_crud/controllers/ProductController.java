package com.test_crud.controllers;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test_crud.entity.ProductEntity;
import com.test_crud.services.ProductService;

@Controller
@RequestMapping("")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "index";
    } 

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("product", new ProductEntity());
        return "add";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "detail";
    }

    @PostMapping("/store")
    public String store(ProductEntity productEntity){
        productEntity.setSlug(toSlug(productEntity.getName()));
        productService.save(productEntity);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, ProductEntity productEntity){
        ProductEntity product = productService.getProductById(id);
        product.setName(productEntity.getName());
        product.setSlug(toSlug(productEntity.getName()));
        product.setCategory(productEntity.getCategory());
        product.setDescription(productEntity.getDescription());
        product.setPrice(productEntity.getPrice());
        product.setStock(productEntity.getStock());

        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id){
        productService.deleteProductById(id);
        return "redirect:/";
    }

    public static String toSlug(String input){
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
