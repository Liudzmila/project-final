package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileRestController profileRestController;

    @Test
    @WithMockUser(username = "user", password = "password")
    void whenGetProfileThenReturnStatusOk() throws Exception {
        mockMvc.perform(get("/api/profile")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString("user:password".getBytes()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void whenUpdateProfileWithValidDataThenReturnNoContent() throws Exception {
        Set<String> mailNotifications = new HashSet<>();
        mailNotifications.add("notification1");
        mailNotifications.add("notification2");

        Set<ContactTo> contacts = new HashSet<>();
        contacts.add(new ContactTo("email", "test@example.com"));
        contacts.add(new ContactTo("phone", "123456789"));

        ProfileTo profileTo = new ProfileTo(1L, mailNotifications, contacts);

        doNothing().when(profileRestController).update(profileTo, 1L);

        mockMvc.perform(put("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileTo)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetProfileFailsThenReturnIsUnauthorized() throws Exception {
        Role role1 = Role.DEV;
        Role role2 = Role.ADMIN;
        Collection<Role> roles = Arrays.asList(role1, role2);

        Long id = 1L;
        String email = "test@example.com";
        String password = "password";
        String firstName = "John";
        String lastName = "Doe";
        String displayName = "johndoe";
        LocalDateTime startpoint = LocalDateTime.now();
        LocalDateTime endpoint = null;

        Set<String> mailNotifications = new HashSet<>();
        mailNotifications.add("notification1");
        mailNotifications.add("notification2");

        Set<ContactTo> contacts = new HashSet<>();
        contacts.add(new ContactTo("email", "test@example.com"));
        contacts.add(new ContactTo("phone", "123456789"));

        ProfileTo profileTo = new ProfileTo(1L, mailNotifications, contacts);

        User user = new User(id, email, password, firstName, lastName, displayName, startpoint, endpoint, roles);

        AuthUser authUser = new AuthUser(user);
        when(profileRestController.get(any(AuthUser.class))).thenReturn(profileTo);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/profile").sessionAttr("authUser", authUser))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void whenUpdateProfileWithInvalidDataThenReturnIsUnprocessableEntity() throws Exception {
        Set<ContactTo> contacts = new HashSet<>();
        contacts.add(new ContactTo("email", null)); // this null IsUnprocessableEntity()
        contacts.add(new ContactTo("phone", null)); // this null IsUnprocessableEntity()

        ProfileTo profileTo = new ProfileTo(null, null, contacts);

        mockMvc.perform(put("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileTo)))
                .andExpect(status().isUnprocessableEntity());
    }
}