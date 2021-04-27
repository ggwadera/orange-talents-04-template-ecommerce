package br.com.zupacademy.ggwadera.mercadolivre.security;

import br.com.zupacademy.ggwadera.mercadolivre.novousuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .map(AuthenticatedUser::new)
            .orElseThrow(() -> new UsernameNotFoundException("Não foi encontrado nenhum usuário com o email: " + email));
    }
}
