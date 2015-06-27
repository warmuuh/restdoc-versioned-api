package wrm;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class UserDocumentation extends DocumentationTestBase {
    
	@Test
	public void user()  throws Exception {
		  this.mockMvc
          .perform(get("/user"))
          .andExpect(status().isOk())
          .andDo(response()
                  .common(fieldWithPath("id").description("user-id"),
                          fieldWithPath("name").description("name of user"))
                  .v1(    fieldWithPath("g").description("gender of the user"))
                  .v2(    fieldWithPath("gender").description("gender of the user"))
                  .build());
	}

}
