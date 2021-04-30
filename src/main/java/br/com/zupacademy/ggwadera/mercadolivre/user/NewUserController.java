package br.com.zupacademy.ggwadera.mercadolivre.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class NewUserController {

  private final UserRepository userRepository;

  @Autowired
  public NewUserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping
  public ResponseEntity<Void> newUser(@RequestBody @Valid NewUserRequest request) {
    userRepository.save(request.toModel());
    return ResponseEntity.ok().build();
  }
}
