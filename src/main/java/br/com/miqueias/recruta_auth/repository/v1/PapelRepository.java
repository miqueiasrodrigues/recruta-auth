package br.com.miqueias.recruta_auth.repository.v1;

import br.com.miqueias.recruta_auth.model.Papel;
import br.com.miqueias.recruta_auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {

}
