package com.dat.backend.userservice;

import com.dat.backend.userservice.dto.CreateUserDto;
import com.dat.backend.userservice.entity.User;
import com.dat.backend.userservice.repository.UserRepository;
import com.dat.backend.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.dat.backend.userservice.dto.UserDto;
import org.mockito.ArgumentCaptor;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUserSuccess() {
        // Given
        CreateUserDto createUserDto = CreateUserDto.builder()
                .email("test@example.com")
                .password("password123")
                .fullName("John Doe")
                .age(25)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .fullName(createUserDto.getFullName())
                .age(createUserDto.getAge())
                .build();

        // Mock hành vi repository
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User userArg = invocation.getArgument(0);
            userArg.setId(1L); // Giả lập DB gán ID
            return userArg;
        });

        // When
        UserDto result = userService.create(createUserDto);

        // Then
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John Doe", result.getFullName());
        assertEquals(25, result.getAge());
        assertEquals(1L, result.getId());

        // Kiểm tra xem user được lưu vào repo có đúng không
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User captured = userCaptor.getValue();
        assertEquals("test@example.com", captured.getEmail());
        assertEquals("password123", captured.getPassword());
        assertEquals("John Doe", captured.getFullName());
        assertEquals(25, captured.getAge());
    }
}
