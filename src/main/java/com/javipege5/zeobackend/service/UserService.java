package com.javipege5.zeobackend.service;

import com.javipege5.zeobackend.entity.User;
import com.javipege5.zeobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List getAll(){
        List<User> list = userRepository.findAll();
        return list;
    }

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}
