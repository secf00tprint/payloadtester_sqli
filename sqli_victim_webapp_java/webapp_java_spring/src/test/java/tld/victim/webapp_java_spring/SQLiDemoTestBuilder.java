package tld.victim.webapp_java_spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SQLiDemoTestBuilder {

    private final MockMvc mockMvc;
    String method = "post";

    private List<String> resultContainsStrings = new ArrayList();
    private String endpoint = null;
    private String data = "";


    public SQLiDemoTestBuilder(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public SQLiDemoTestBuilder post()
    {
        this.method = "post";
        return this;
    }

    public SQLiDemoTestBuilder delete()
    {
        this.method = "delete";
        return this;
    }

    public SQLiDemoTestBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public SQLiDemoTestBuilder data(String key, String value) {
        this.data = this.data + key + "=" + value + "&";
        return this;
    }

    public SQLiDemoTestBuilder resultContains(String resultContainsString) {
        this.resultContainsStrings.add(resultContainsString);
        return this;
    }

    public void run() throws Exception {

        this.data = StringUtils.chop(this.data);

        assertFalse((this.endpoint == null));
        assertFalse(resultContainsStrings.isEmpty());

        switch(method)
        {
            case "post":
                        {
                            ResultActions currentTestContext = this.mockMvc.perform(MockMvcRequestBuilders.post(this.endpoint)
                                    .content(this.data)
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                    .accept(MediaType.ALL_VALUE))
                                    .andDo(print()).andExpect(status().isOk());
                            checkResponse(currentTestContext);
                            break;
                        }
            case "delete":
                        {
                            ResultActions currentTestContext = this.mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete(this.endpoint)
                                    .content(this.data)
                                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                    .accept(MediaType.ALL_VALUE))
                                    .andDo(print()).andExpect(status().isOk());
                                     checkResponse(currentTestContext);
                            break;
                        }
        }


    }

    private void checkResponse(ResultActions resultActions) throws Exception {
        for (String resultString : resultContainsStrings)
        {
            resultActions.andExpect(content().string(containsString(resultString)));
        }
    }
}
