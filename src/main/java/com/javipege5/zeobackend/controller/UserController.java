package com.javipege5.zeobackend.controller;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.javipege5.zeobackend.DTO.Message;
import com.javipege5.zeobackend.DTO.UserList;
import com.javipege5.zeobackend.entity.User;
import com.javipege5.zeobackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin()
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @GetMapping("/list")
    public ResponseEntity<List> getList(){
        List list = userService.getAll();
        ModelMapper mapper = new ModelMapper();
        List usersList = mapper.map(list, new TypeToken<List<UserList>>(){}.getType());
        return new ResponseEntity<List>(usersList, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(!StringUtils.hasText(user.getName()) )
            return new ResponseEntity(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(!StringUtils.hasText(user.getEmail()))
            return new ResponseEntity(new Message("El email es obligatorio"), HttpStatus.BAD_REQUEST);

        if(!StringUtils.hasText(user.getPassword())){
            return new ResponseEntity(new Message("El password es obligatorio"), HttpStatus.BAD_REQUEST);
        }

        if(userService.existsByEmail(user.getEmail()))
            return new ResponseEntity(new Message("Este email ya existe en nuestra base de datos persistente"), HttpStatus.BAD_REQUEST);

        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.save(user);
        ObjectNode res = prepareResponse( new Message("Usuario registrado correctamente en la base de datos persistente").getMessage(), user.getName());
        return new ResponseEntity(res, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user){

        if(!StringUtils.hasText(user.getEmail()))
            return new ResponseEntity(new Message("El email es obligatorio"), HttpStatus.BAD_REQUEST);

        if(!StringUtils.hasText(user.getPassword()))
            return new ResponseEntity(new Message("El password es obligatorio"), HttpStatus.BAD_REQUEST);

        Optional<User> dbUser = userService.getByEmail(user.getEmail());
        if(!dbUser.isPresent())
            return new ResponseEntity(new Message("Este email no está registrado en la base de datos persistente"), HttpStatus.NOT_FOUND);

        Boolean check = encoder.matches(user.getPassword(),dbUser.get().getPassword());

        if(!check)
            return new ResponseEntity(new Message("Contraseña incorrecta"), HttpStatus.BAD_REQUEST);

        ObjectNode res = prepareResponse( new Message("Usuario logeado correctamente en la base de datos persistente").getMessage(), dbUser.get().getName());
        return new ResponseEntity(res, HttpStatus.OK);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!userService.existsById(id))
            return new ResponseEntity(new Message("no existe ese usuario"), HttpStatus.NOT_FOUND);
        userService.delete(id);
        return new ResponseEntity(new Message("usuario eliminado"), HttpStatus.OK);
    }

    private ObjectNode prepareResponse(String message, String userName){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("message", message);
        objectNode.put("userName",userName);
        return objectNode;
    }

}
