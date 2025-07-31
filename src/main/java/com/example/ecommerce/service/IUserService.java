package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import java.util.List;

public interface IUserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    String deleteUser(Long id);
}
