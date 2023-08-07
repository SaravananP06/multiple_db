package com.javapractice.db.multiple_db.mysql.repo;

import com.javapractice.db.multiple_db.mysql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findByName(String Name);
}
