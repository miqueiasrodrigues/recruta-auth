package br.com.miqueias.recruta_auth.service.v1.impl;

import br.com.miqueias.recruta_auth.dto.v1.papel.PapelAssociateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioChangePasswordRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioCreateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioUpdateRequest;
import br.com.miqueias.recruta_auth.exception.CpfAlreadyInUseException;
import br.com.miqueias.recruta_auth.exception.EmailAlreadyInUseException;
import br.com.miqueias.recruta_auth.exception.ResourceNotFoundException;
import br.com.miqueias.recruta_auth.mapper.v1.UsuarioMapper;
import br.com.miqueias.recruta_auth.model.Papel;
import br.com.miqueias.recruta_auth.model.Usuario;
import br.com.miqueias.recruta_auth.repository.v1.PapelRepository;
import br.com.miqueias.recruta_auth.repository.v1.UsuarioRepository;
import br.com.miqueias.recruta_auth.security.UsuarioDetalhe;
import br.com.miqueias.recruta_auth.service.v1.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PapelRepository papelRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.papelRepository = papelRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UsuarioResponse create(UsuarioCreateRequest usuarioCreateRequest) {
        Usuario usuario = usuarioMapper.toEntity(usuarioCreateRequest);

        Optional<Usuario> emailExiste = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExiste.isPresent()) {
            throw new EmailAlreadyInUseException("Email já está em uso");
        }

        Optional<Usuario> cpfExiste = usuarioRepository.findByCpf(usuario.getCpf());
        if (cpfExiste.isPresent()) {
            throw new CpfAlreadyInUseException("CPF já está em uso");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Papel papel = papelRepository.findById(2L).orElseThrow(
                () -> new ResourceNotFoundException("Papel não encontrado")
        );

        usuario.getPapeis().add(papel);

        Usuario usuarioUpated = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioUpated);
    }

    @Transactional
    @Override
    public UsuarioResponse update(UsuarioUpdateRequest usuarioUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetalhe usuarioDetalhe = (UsuarioDetalhe) authentication.getPrincipal();


        Usuario usuarioAutenticado = usuarioRepository.findByCpf(usuarioDetalhe.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autenticado não encontrado"));


        Usuario usuario = usuarioRepository.findById(
                usuarioUpdateRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        boolean isAdmin = usuarioAutenticado.getPapeis().stream()
                .anyMatch(role -> role.getDescricao().equals("ROLE_ADMIN"));

        if (!isAdmin && !usuarioAutenticado.getId().equals(usuario.getId())) {
            throw new SecurityException("Você não tem permissão para editar este usuário");
        }


        usuario.setNome(usuarioUpdateRequest.getName());
       usuario.setSobrenome(usuarioUpdateRequest.getLastname());

       Usuario usuarioUpated = usuarioRepository.save(usuario);

       return usuarioMapper.toDTO(usuarioUpated);
    }

    @Transactional
    @Override
    public UsuarioResponse changePassword(UsuarioChangePasswordRequest usuarioChangePasswordRequest) {
        Usuario usuario = usuarioRepository.findById(
                usuarioChangePasswordRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode(usuarioChangePasswordRequest.getPassword()));

        Usuario usuarioUpated = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioUpated);
    }

    @Transactional
    @Override
    public UsuarioResponse associateRole(PapelAssociateRequest papelAssociateRequest) {
        Papel papel = papelRepository.findById(papelAssociateRequest.getPapelId()).orElseThrow(
                () -> new ResourceNotFoundException("Papel não encontrado")
        );

        Usuario usuario = usuarioRepository.findById(papelAssociateRequest.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.getPapeis().add(papel);
        Usuario usuarioUpdated = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioUpdated);
    }
}
