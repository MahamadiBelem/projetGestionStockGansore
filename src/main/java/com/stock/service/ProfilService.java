package com.stock.service;

import com.stock.domain.Authority;
import com.stock.domain.Profil;
import com.stock.domain.User;
import com.stock.repository.AuthorityRepository;
import com.stock.repository.ProfilRepository;
import com.stock.repository.UserRepository;
import com.stock.service.dto.ProfilDTO;
import com.stock.service.mapper.ProfilMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Profil}.
 */
@Service
@Transactional
public class ProfilService {

    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;
    private final CacheManager cacheManager;

    private final ProfilMapper profilMapper;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public ProfilService(ProfilRepository profilRepository, CacheManager cacheManager, ProfilMapper profilMapper, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.profilRepository = profilRepository;
        this.cacheManager = cacheManager;
        this.profilMapper = profilMapper;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    /**
     * Save a profil.
     *
     * @param profilDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfilDTO save(ProfilDTO profilDTO) {
        log.debug("Request to save Profil : {}", profilDTO);
        Profil profil = profilMapper.toEntity(profilDTO);
        profil.setAuthorities(authoritiesFromStrings(profilDTO.getRoles()));
        profil = profilRepository.save(profil);
        if(profilDTO.getId()!=null) { //update
            List<User> usersHavingProfil= userRepository.findAllByProfilId(profilDTO.getId());
            if(usersHavingProfil!=null) {
                Profil newProfil = profil;
                usersHavingProfil.forEach(user -> {
                    Set<Authority> authorities= new HashSet<>();
                    Set<Authority> newAuthorities= newProfil.getAuthorities();
                    if(newAuthorities!=null) {
                        newAuthorities.forEach(item-> {
                            authorities.add(authorityRepository.getOne(item.getName()));
                        });
                    }
                    if(!authorities.contains("ROLE_USER")) {
                        authorities.add(authorityRepository.getOne("ROLE_USER"));
                    }
                    user.setAuthorities(authorities);
                    userRepository.save(user);
                    this.clearUserCaches(user);
                });
            }
        }
        profilDTO = profilMapper.toDto(profil);
        profilDTO.setRoles(profil.getAuthorities()
            .stream().map(Authority::getName)
            .collect(Collectors.toSet()));
        return profilDTO;
    }

    /**
     * Get all the profils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profils");
        return profilRepository.findAll(pageable)
            .map(profilMapper::toDto);
    }


    /**
     * Get one profil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfilDTO> findOne(Long id) {
        log.debug("Request to get Profil : {}", id);
        return profilRepository.findById(id)
            .map(profilMapper::toDto);
    }

    /**
     * Delete the profil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        System.out.println("New profil "+authoritiesAsString);
        Set<Authority> authorities = new HashSet<>();
        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }
        return authorities;
    }
    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
