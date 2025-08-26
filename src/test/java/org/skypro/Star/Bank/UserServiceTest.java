package org.skypro.Star.Bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.Star.Bank.service.UserService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserService userService;

    @Test
    void findUsersByName_ShouldReturnMatchingUsers(){
        User expectedUser = new User(UUID.randomUUID(), " Иван ", " Иванов ");

        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), anyString()))
                .thenReturn(List.of(expectedUser));

        List<User> result = userService.findUsersByName("Иван");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Иван");
        assertThat(result.get(0).getSurname()).isEqualTo("Иванов");
    }
    @Test
    void findUsersByName_ShouldReturnEmptyListWhenNoMatches() {
        when(jdbcTemplate.query(anyString(), any(BeanPropertyRowMapper.class), anyString())).thenReturn(Collections.emptyList());

        List<User> result = userService.findUsersByName(" Несуществующий ");
        assertThat(result).isEmpty();
    }
}

