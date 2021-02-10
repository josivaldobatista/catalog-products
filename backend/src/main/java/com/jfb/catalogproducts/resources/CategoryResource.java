package com.jfb.catalogproducts.resources;

import java.util.ArrayList;
import java.util.List;

import com.jfb.catalogproducts.entities.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

  @GetMapping
  public ResponseEntity<List<Category>> findAll() {
    List<Category> list = new ArrayList<>();
    list.add(new Category(1L, "Informática"));
    list.add(new Category(2L, "Livros"));
    return ResponseEntity.ok().body(list);
  }

}