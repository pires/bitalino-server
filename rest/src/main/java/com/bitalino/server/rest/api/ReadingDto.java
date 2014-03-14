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
package com.bitalino.server.rest.api;

import java.util.ArrayList;
import java.util.List;

import com.bitalino.server.model.BITalinoFrame;
import com.bitalino.server.model.BITalinoReading;

/**
 * Data transfer object for {@link BITalinoReading} entity.
 */
public class ReadingDto {

  private long timestamp;
  private List<FrameDto> frames;

  public ReadingDto() {
    this.frames = new ArrayList<FrameDto>();
  }

  public ReadingDto(final long timestamp, final List<FrameDto> frames) {
    this.timestamp = timestamp;
    this.frames = frames;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public List<FrameDto> getFrames() {
    return frames;
  }

  public void setFrames(List<FrameDto> frames) {
    this.frames = frames;
  }

  public void addFrame(final FrameDto frame) {
    this.frames.add(frame);
  }

  public static ReadingDto fromEntity(final BITalinoReading entity) {
    ReadingDto dto = new ReadingDto();
    dto.setTimestamp(entity.getTimestamp());
    List<FrameDto> readings = new ArrayList<FrameDto>(entity.getFrames().size());
    for (BITalinoFrame frame : entity.getFrames())
      readings.add(FrameDto.fromEntity(frame));
    return dto;
  }

  public static List<ReadingDto> fromEntities(
      final List<BITalinoReading> entities) {
    List<ReadingDto> dtos = new ArrayList<>(entities.size());
    for (BITalinoReading entity : entities)
      dtos.add(fromEntity(entity));
    return dtos;
  }

  public static BITalinoReading toEntity(final ReadingDto dto) {
    BITalinoReading entity = new BITalinoReading();
    List<BITalinoFrame> frames = new ArrayList<BITalinoFrame>();
    for (FrameDto fdto : dto.getFrames())
      frames.add(FrameDto.toEntity(fdto));
    entity.setFrames(frames);
    entity.setTimestamp(dto.getTimestamp());
    return entity;
  }

  @Override
  public String toString() {
    return "ReadingDto [timestamp=" + timestamp + ", frames=" + frames.size() + "]";
  }

}