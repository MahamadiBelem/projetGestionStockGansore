package com.stock.repository;

import com.stock.domain.Profil;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Profil findByNomProfil(String invite);

    @Query("select p from  Profil p where p.id=:id")
    public Profil findOne(@Param("id") Long id);
}
