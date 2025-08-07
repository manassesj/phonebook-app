package com.phonebook.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.mockito.Mockito.*;

class CorsConfigTest {

    @Test
    void corsConfigurer_shouldAddCorsMappingsWithExpectedSettings() {
        // Arrange
        CorsConfig corsConfig = new CorsConfig();
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();

        CorsRegistry corsRegistry = mock(CorsRegistry.class);

        var corsRegistration = mock(CorsRegistration.class);

        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);

        when(corsRegistration.allowedOrigins("http://localhost:4200")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("*")).thenReturn(corsRegistration);

        configurer.addCorsMappings(corsRegistry);

        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOrigins("http://localhost:4200");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
        verify(corsRegistration).allowedHeaders("*");
    }
}
