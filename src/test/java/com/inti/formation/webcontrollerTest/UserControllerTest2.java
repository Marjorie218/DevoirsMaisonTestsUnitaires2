package com.inti.formation.webcontrollerTest;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inti.formation.MiniProjet1Application;
import com.inti.formation.dao.UserDao;
import com.inti.formation.dao.UserDaoTest;
import com.inti.formation.entity.User;
import com.inti.formation.service.UserService;
import com.inti.formation.webcontroller.UserController;



@WebAppConfiguration // adresse des requêtes web
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MiniProjet1Application.class)
public class UserControllerTest2 {

	@Autowired
	WebApplicationContext webApplicationContext; // object Srping framework executer des requêtes web
	protected MockMvc mvc; // type d'objet pour rendre le contexte faux - simulation
	protected String uri; // contient l'url

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		// construit une fausse image du webapplicationcontext
		// .build fait parti de lombock
		MockitoAnnotations.initMocks(this); // au cas où on a des mock
	}
	
	

	@InjectMocks
	private UserController userControllertoMock;
	@Autowired
	private UserController userController;
	@Mock
	private UserService userService;
	@Mock 
	private User user;
	@Mock
	private UserDao userDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoTest.class);

	public UserControllerTest2() {
		super();
		this.uri = "/user";
	}

	
	@Test
	public void getAllEntityList_status() {
		MvcResult mvcResult;
		try {
			LOGGER.info("-------- Testing getAllEntity Method --------");
			LOGGER.info("-------- Contructing Utilisateur --------");
			LOGGER.info("-------- Saving Utilisateur --------");
			userService.addUser(new User(2, "dalii"));
			mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + "/all").accept(MediaType.APPLICATION_JSON_VALUE))
					.andReturn();
			LOGGER.info("-------- Getting HTTP Status --------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("-------- Verifying HTTP Status --------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// à modifier
	@Test
	public void getAllEntityList_works() {
		try {
			LOGGER.info("-------- Testing getAllEntity Method --------");
			LOGGER.info("-------- Contructing Utilisateur --------");
			User user = new User(2, "dalii");
			LOGGER.info("-------- getAll users --------");
			userControllertoMock.getAllUsers();
			LOGGER.info("-------- getAll users --------");
			Mockito.verify(userService).getAllUsers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createEntity_status200() {
		LOGGER.info("-------- Testing createEntity Method --------");
		LOGGER.info("-------- Constructing Utilisateur --------");
		User user = new User(50, "sala7");
		MvcResult mvcResult;
		try {
			LOGGER.info("-------- Serializing Utilisateur Object --------");
			String inputJson = this.mapToJson(user);
			LOGGER.info("-------- Mocking Context Webservice and invoking the webservice --------");
			mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + "/adduser")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("-------- Getting HTTP Status --------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("-------- Verrifying HTTP Status --------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void createEntity_works() {
		LOGGER.info("-------- Testing createEntity Method --------");
		LOGGER.info("-------- Constructing Utilisateur --------");
		try {
			User user = new User(50, "sala7");
			LOGGER.info("-------- Creating Utilisateur --------");
			userControllertoMock.addNewUser(user);
			LOGGER.info("-------- Creating Utilisateur --------");
			Mockito.verify(userService).addUser(user);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateEntity_status200() {
		try {
			LOGGER.info("-------- Testing updateEntity Method --------");
			LOGGER.info("-------- Constructing Utilisateur --------");
			User oldUser = new User(2, "Lemon");
			LOGGER.info("-------- Saving Utilisateur --------");
			userService.addUser(oldUser);
			LOGGER.info("-------- Modifying Utilisateur--------");
			User newUser = new User(2, "Lemonade");
			LOGGER.info("-------- Serializing Utilisateur Object--------");
			String inputJson = this.mapToJson(newUser);
			LOGGER.info("-------- Mocking Context Webservice and invoking the webservice --------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + "/2")
					.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			LOGGER.info("-------- Getting HTTP Status --------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("-------- Verrifying HTTP Status--------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateEntity_works() {
		try {
			LOGGER.info("-------- Testing updateEntity Method --------");
			LOGGER.info("-------- Constructing Utilisateur --------");
			User user = new User(2,"Lemon");
			LOGGER.info("-------- Saving utilisateur --------");
			Mockito.when(userService.getUserById(2L)).thenReturn(user);
			LOGGER.info("-------- Saving utilisateur --------");
			Mockito.when(userService.addUser(any(User.class))).thenReturn(user);
			LOGGER.info("-------- Saving utilisateur --------");
			LOGGER.info("-------- Saving utilisateur --------");
			userControllertoMock.updateUser(user, user.getId());
			LOGGER.info("-------- Saving utilisateur --------");
			Mockito.verify(userDao);
			Mockito.verify(userService).updateUser(user);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteEntity_status200() {
		LOGGER.info("-------- Testing deleteEntity Method --------");
		try {
			LOGGER.info("-------- Constructing Utilisateur --------");
			LOGGER.info("-------- Saving Utilisateur --------");
			userService.addUser(new User(2, "Lemon"));
			LOGGER.info("-------- Mocking Context Webservice and invoking the webservice --------");
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + "/2")).andReturn();
			LOGGER.info("-------- Getting HTTP Status --------");
			int status = mvcResult.getResponse().getStatus();
			LOGGER.info("-------- Verrifying HTTP Status --------");
			assertEquals(200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteEntity_works() {
		LOGGER.info("-------- Testing deleteEntity Method --------");
		try {
			LOGGER.info("-------- Constructing Utilisateur --------");
			User user = new User(2,"Lemon");
			LOGGER.info("-------- delete Utilisateur --------");
			Mockito.when(userService.getUserById(2L)).thenReturn(user);
			LOGGER.info("-------- delete Utilisateur --------");
			userControllertoMock.deleteUser(user.getId());
			LOGGER.info("-------- delete Utilisateur --------");
			Mockito.verify(userService).deleteUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected final <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
