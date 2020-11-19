package com.github.DenPeshkov.role;

import com.github.DenPeshkov.role.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Set<Role> getRoles(Collection<RoleDto> rolesDto) {
    return rolesDto == null
        ? Collections.emptySet()
        : rolesDto.stream()
            .map(RoleDto::getName)
            .map(roleRepository::findByName)
            .collect(Collectors.toSet());
  }
}
