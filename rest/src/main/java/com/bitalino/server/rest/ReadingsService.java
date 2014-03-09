/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bitalino.server.rest;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitalino.server.dao.BITalinoReadingDao;
import com.bitalino.server.model.BITalinoReading;
import com.bitalino.server.rest.api.ReadingDto;

@Path("/")
public class ReadingsService {

  private static final Logger logger = LoggerFactory
      .getLogger(ReadingsService.class);

  @EJB
  private BITalinoReadingDao dao;

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response handleNewReading(final ReadingDto dto) {
    logger.info("Received new reading {}", dto);
    checkNotNull(dto);
    dao.create(ReadingDto.toEntity(dto));
    return Response.ok().build();
  }

  /**
   * Retrieves all {@link BITalinoReading} related to a time interval.
   * 
   * @param since
   *          the last query timestamp
   * @return list of {@link BITalinoReading}.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<ReadingDto> retrieveReadingsSince(
      @QueryParam("since") final long since) {
    return ReadingDto.fromEntities(dao.find_all_readings_since(since));

  }

}