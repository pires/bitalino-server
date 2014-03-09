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
package com.bitalino.server.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;

import com.bitalino.server.model.BITalinoReading;
import com.bitalino.server.model.BITalinoReading_;

/**
 * Data access object for {@link BITalinoReading} entity.
 */
@Stateless
public class BITalinoReadingDao extends AbstractDao<BITalinoReading> {

  public BITalinoReadingDao() {
    super(BITalinoReading.class);
  }

  /**
   * Finds all {@link BITalinoReading} related to a time interval.
   * 
   * @param since
   * 
   * @return a list of all {@link BITalinoReading} related to a time interval.
   */
  public List<BITalinoReading> find_all_readings_since(final long since) {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<BITalinoReading> cq = cb.createQuery(BITalinoReading.class);
    Root<BITalinoReading> reading = cq.from(BITalinoReading.class);

    Predicate interval = cb.between(reading.get(BITalinoReading_.timestamp),
        since, DateTime.now().getMillis());
    cq.where(interval);

    return getEntityManager().createQuery(cq).getResultList();
  }

}