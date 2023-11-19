package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@Validated
@ResponseBody
@RequestMapping("/api/admin")
public class RestAdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<User> showAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>((User) authentication.getPrincipal(), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> index() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/role")
    public ResponseEntity<Role> showRole(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(roleService.findByName(name), HttpStatus.OK);
    }

    @GetMapping("/users/user")
    public ResponseEntity<User> showUser(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(userService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<List<User>> delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/users/update")
    public ResponseEntity<List<User>> update(@RequestParam(value = "ids") @NotBlank(message = "не выбрана роль") String ids, @RequestParam(value = "id") Long id,
                                             @RequestBody @Valid User user, BindingResult bindingResult) {
            userService.update(id, user, List.of(ids));
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/users/new")
    public ResponseEntity<List<User>> create(@RequestParam(value = "ids") @NotBlank String ids,
                                             @Valid @RequestBody User user, BindingResult bindingResult) {
        userService.save(user, List.of(ids));
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
