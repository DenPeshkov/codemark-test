package com.github.DenPeshkov.role;

import com.github.DenPeshkov.role.dto.RoleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
  @Mock RoleRepository roleRepository;

  @InjectMocks RoleService roleService;

  @Test
  void getRoles() {
    Role role1 = new Role("role1");
    role1.setId(0L);
    Role role2 = new Role("role2");
    role2.setId(34L);
    Role role3 = new Role("role3");
    role3.setId(5L);

    when(roleRepository.findByName(role1.getName())).thenReturn(role1);
    when(roleRepository.findByName(role2.getName())).thenReturn(role2);
    when(roleRepository.findByName(role3.getName())).thenReturn(role3);

    Set<RoleDto> roleDtos =
        Set.of(new RoleDto("role1"), new RoleDto("role2"), new RoleDto("role3"));
    assertThat(roleService.getRoles(roleDtos))
        .allMatch(role -> !role.getName().isEmpty() && role.getId() != null);
  }
}
