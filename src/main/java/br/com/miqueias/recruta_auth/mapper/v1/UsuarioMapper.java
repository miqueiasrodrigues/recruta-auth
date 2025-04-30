package br.com.miqueias.recruta_auth.mapper.v1;

import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioCreateRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioLoginRequest;
import br.com.miqueias.recruta_auth.dto.v1.usuario.UsuarioResponse;
import br.com.miqueias.recruta_auth.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(source = "name", target = "nome")
    @Mapping(source = "lastname", target = "sobrenome")
    @Mapping(source = "password", target = "senha")
    Usuario toEntity(UsuarioCreateRequest usuarioCreateRequest);

    @Mapping(source = "password", target = "senha")
    Usuario toEntity(UsuarioLoginRequest usuarioLoginRequest);

    @Mapping(source = "nome", target = "name")
    @Mapping(source = "sobrenome", target = "lastname")
    UsuarioResponse toDTO(Usuario usuario);


}
