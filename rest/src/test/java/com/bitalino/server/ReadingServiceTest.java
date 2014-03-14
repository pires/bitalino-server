package com.bitalino.server;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.bitalino.server.dao.BITalinoReadingDao;
import com.bitalino.server.rest.api.FrameDto;
import com.bitalino.server.rest.api.ReadingDto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReadingServiceTest {

  private BITalinoReadingDao dao;

  private static final String URL = "http://localhost:8181/bitalino";
  private final Client client = ClientBuilder.newClient();
  private long since = DateTime.now().getMillis();
  private final int max = 10;

  private final Type readingType = new TypeToken<List<ReadingDto>>() {
  }.getType();

  @BeforeClass
  private void setUp() throws NamingException {
    dao = (BITalinoReadingDao) getBean("java:global/bitalino/BITalinoReadingDao");
  }

  @Test
  public void test_new_readings() {
    assertEquals(dao.count(), 0);
    ReadingDto reading = new ReadingDto();
    reading.setTimestamp(since);
    for (int counter = 0; counter < max; counter++) {
      FrameDto frame = new FrameDto();
      final int[] analog = { counter, 0, 0, 0, 0, counter * counter };
      frame.setAnalog(analog);
      reading.addFrame(frame);

      Response response = client.target(URL)
          .request(MediaType.APPLICATION_JSON).put(Entity.json(reading));
      assertEquals(response.getStatus(), 200);
    }

    assertEquals(dao.count(), max);
  }

  @Test(dependsOnMethods = { "test_new_readings" })
  public void test_readings_since() {
    Response response = client.target(URL).queryParam("since", since).request()
        .accept(MediaType.APPLICATION_JSON).get();
    assertEquals(response.getStatus(), 200);
    final String json = response.readEntity(String.class);
    final List<ReadingDto> dtos = new Gson().fromJson(json, readingType);
    assertEquals(dtos.size(), dao.find_all_readings_since(since).size());
  }

  private static Object getBean(String entity) throws NamingException {
    return new InitialContext().lookup(entity);
  }

}
