package com.helmes.helmes_test.domain.user.api.external.v1;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.helmes.helmes_test.domain.sector.model.Sector;
import com.helmes.helmes_test.domain.sector.service.SectorService;
import com.helmes.helmes_test.domain.user.model.User;
import com.helmes.helmes_test.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String INDEX_URL = "/";
    private static final String SAVE_URL = "/save";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SectorService sectorService;

    @Nested
    class Index {

        @Test
        public void withPassedUserObjectShouldReturnOkAndContainUserAndSectorsAsAttributes() throws Exception {
            var user = makeUser();

            when(sectorService.getAllFlattenedWithIndentation()).thenReturn(user.getSectors());

            mockMvc
                    .perform(MockMvcRequestBuilders.get(INDEX_URL).flashAttr("user", user))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("index"))
                    .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                    .andExpect(MockMvcResultMatchers.model().attributeExists("sectors"))
                    .andExpect(MockMvcResultMatchers.model().attribute("user", user))
                    .andExpect(MockMvcResultMatchers.model().attribute("sectors", user.getSectors()));
        }
    }

    @Nested
    class Save {

        @Test
        public void withEmptyUserFieldsShouldReturnClientError() throws Exception {
            mockMvc
                    .perform(MockMvcRequestBuilders.post(SAVE_URL).flashAttr("user", new User()))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Name should not be empty")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.sectors",
                                                              is("User should have at least one selected sector")
                    ));

            verify(userRepository, times(0)).save(any(User.class));
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "Friedrich Wilhelm krahv von Buxhoevden"})
        public void withNotValidUserNameShouldReturnClientError(String userName) throws Exception {
            var user = makeUser();
            user.setName(userName);

            mockMvc.perform(MockMvcRequestBuilders.post(SAVE_URL).flashAttr("user", user))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andExpect(MockMvcResultMatchers.jsonPath("$", aMapWithSize(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                                                              is("Name should be between 2 and 30 characters")
                    ));

            verify(userRepository, times(0)).save(any(User.class));
        }

        @Test
        public void withValidUserShouldSaveItToDatabaseAndRedirectToIndex() throws Exception {
            var user = makeUser();

            mockMvc.perform(MockMvcRequestBuilders.post(SAVE_URL).flashAttr("user", user))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.redirectedUrl(INDEX_URL));

            verify(userRepository, times(1)).save(user);
        }
    }

    private User makeUser() {
        var user = new User();
        user.setName("testName");
        user.setSectors(List.of(new Sector()));

        return user;
    }
}
