package io.thorntail.mozzy.api;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import io.thorntail.mozzy.domain.model.Person;
import io.thorntail.mozzy.domain.service.PersonRepository;

@RunWith(Arquillian.class)
public class JAXRSApiTest {

	@Deployment
	public static Archive createDeployment() throws Exception {
		JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
		deployment.addPackage(Person.class.getPackage());
		deployment.addPackage(JaxRsActivator.class.getPackage());
		deployment.addPackage(PersonRepository.class.getPackage());
		deployment.addAsWebInfResource(new File("src/test/resources/beans.xml"));
		deployment.addAsWebInfResource(new File("src/test/resources/import.sql"), "classes/import.sql");
		deployment.addAsWebInfResource(
				new ClassLoaderAsset("META-INF/it-persistence.xml", JAXRSApiTest.class.getClassLoader()),
				"classes/META-INF/persistence.xml");
		deployment.addAsWebInfResource(
				new ClassLoaderAsset("META-INF/apache-deltaspike.properties", JAXRSApiTest.class.getClassLoader()),
				"classes/META-INF/apache-deltaspike.properties");
		deployment.addAsWebInfResource(new File("src/test/resources/project-it.yaml"),
				"classes/project-defaults.yaml");
		deployment.addAllDependencies();
		return deployment;
	}

	public static final String API_URL = "http://localhost:8080/api/persons";

	Client client = ClientBuilder.newBuilder().build();

	WebTarget target;

	@Before
	public void before() {
		target = client.target(API_URL);
	}

	@Test
	@RunAsClient
	public void shouldGetAll() {
		Response response = target.request().get();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	@RunAsClient
	public void shouldCreateANewPerson() {
		Person person = new Person();
		person.setDocumentId("a607feec176eb4b4fc32d7ca69f8e343");
		person.setName("MARIO DO ARMARIO");
		Response response = target.request().post(Entity.entity(person, MediaType.APPLICATION_JSON));
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		assertThat(response.getHeaderString("Location")).startsWith("http://localhost:8080/api/persons/");
		response.close();
		Response getPerson = client.target(response.getHeaderString("Location")).request().get();
		Person foundPerson = getPerson.readEntity(Person.class);
		getPerson.close();
		assertThat(foundPerson.getDocumentId()).isEqualTo(person.getDocumentId());
	}

	@Test
	@RunAsClient
	public void shouldPut() {
		target = client.target(API_URL + "/1");
		Person person = new Person();
		person.setId(1L);
		person.setDocumentId("66677fb9980dcc0f996c91e08ef6d6de");
		person.setName("ELIS SABELLA");
		Response response = target.request().put(Entity.entity(person, "application/json"));
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	@RunAsClient
	public void shouldGetByName() {
		target = client.target(API_URL + "/?name=J");
		Response response = target.request().get();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	@RunAsClient
	public void shouldGetByNameAndDocumentID() {
		target = client.target(API_URL + "/?name=P&document-id=9e32f1112a8d64e75fea11c65c99f2ad");
		Response response = target.request().get();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

	@Test
	@RunAsClient
	public void shouldGetByDocumentID() {
		target = client.target(API_URL + "/?document-id=9e32f1112a8d64e75fea11c65c99f2ad");
		Response response = target.request().get();
		assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
	}

}
