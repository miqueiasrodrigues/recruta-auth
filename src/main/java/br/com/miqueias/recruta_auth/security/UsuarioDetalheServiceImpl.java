package br.com.miqueias.recruta_auth.security;


import br.com.miqueias.recruta_auth.exception.NotActiveException;
import br.com.miqueias.recruta_auth.exception.ResourceNotFoundException;
import br.com.miqueias.recruta_auth.model.Usuario;
import br.com.miqueias.recruta_auth.repository.v1.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalheServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioDetalheServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDetalhe loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCpf(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));


        if(!usuario.isAtivo()){
            throw new NotActiveException("Usuário está inativo");
        }

        return new UsuarioDetalhe(usuario);
    }

    public UsuarioDetalhe loadUserById(Long id) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!usuario.isAtivo()) {
            throw new NotActiveException("Usuário está inativo");
        }

        return new UsuarioDetalhe(usuario);
    }


}
