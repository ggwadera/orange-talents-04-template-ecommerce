package br.com.zupacademy.ggwadera.mercadolivre.novousuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
