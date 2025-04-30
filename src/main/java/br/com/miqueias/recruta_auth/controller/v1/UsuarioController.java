package br.com.miqueias.recruta_auth.controller.v1;

import br.com.miqueias.recruta_auth.dto.v1.papel.PapelAssociateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioChangePasswordRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioCreateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioResponse;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioUpdateRequest;
import br.com.miqueias.recruta_auth.service.v1.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioCreateRequest usuarioCreateRequest) {
        UsuarioResponse usuarioResponse = usuarioService.create(usuarioCreateRequest);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<UsuarioResponse> update(@RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
        UsuarioResponse usuarioResponse = usuarioService.update(usuarioUpdateRequest);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @PatchMapping("/senha")
    public ResponseEntity<UsuarioResponse> changePassword(@RequestBody UsuarioChangePasswordRequest usuarioChangePasswordRequest) {
        UsuarioResponse usuarioResponse = usuarioService.changePassword(usuarioChangePasswordRequest);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }

    @PostMapping("/papel")
    public ResponseEntity<UsuarioResponse> associateRole(@RequestBody PapelAssociateRequest papelAssociateRequest) {
        UsuarioResponse usuarioResponse = usuarioService.associateRole(papelAssociateRequest);
        return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
    }
}
