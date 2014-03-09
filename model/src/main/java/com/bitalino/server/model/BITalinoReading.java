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
package com.bitalino.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "BITALINO_READINGS",
    indexes = { @Index(columnList = "timestamp") })
public class BITalinoReading {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @Version
  private long version;

  @Column(name = "timestamp", nullable = false)
  private long timestamp;

  @OneToMany(cascade = { CascadeType.ALL },
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  private List<BITalinoFrame> frames;

  public BITalinoReading() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public List<BITalinoFrame> getFrames() {
    return frames;
  }

  public void setFrames(List<BITalinoFrame> frames) {
    this.frames = frames;
  }

  public void addFrame(BITalinoFrame frame) {
    this.frames.add(frame);
  }

}