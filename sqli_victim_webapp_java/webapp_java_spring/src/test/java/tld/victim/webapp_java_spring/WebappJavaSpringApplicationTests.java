package tld.victim.webapp_java_spring;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class WebappJavaSpringApplicationTests {

	@Rule
	public OutputCaptureRule output = new OutputCaptureRule();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(post("/sqlidemo/add")
				.content("username=testuser&password=password")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		        .accept(MediaType.ALL_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Saved")));

		this.mockMvc.perform(post("/sqlidemo/vulnbyid")
				.content("id=1%27+UNION+SELECT+NULL%2CNULL%2C%28SELECT+%40%40VERSION%29+--+")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.accept(MediaType.ALL_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("10.3")))
				.andExpect(content().string(containsString("MariaDB")))
				.andExpect(content().string(containsString("ubuntu")));

		this.mockMvc.perform(delete("/sqlidemo/del")
				.content("username=testuser")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.accept(MediaType.ALL_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Deleted")));;
	}

}
