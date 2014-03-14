package com.bitalino.server.rest.api;

import java.util.Arrays;

import com.bitalino.server.model.BITalinoFrame;

public class FrameDto {

  private int crc;
  private int seq;
  private int[] analog = new int[6];
  private int[] digital = new int[4];

  public FrameDto() {
  }

  public int getCrc() {
    return crc;
  }

  public void setCrc(int crc) {
    this.crc = crc;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public int[] getAnalog() {
    return analog;
  }

  public void setAnalog(int[] analog) {
    this.analog = analog;
  }

  public int[] getDigital() {
    return digital;
  }

  public void setDigital(int[] digital) {
    this.digital = digital;
  }

  public static FrameDto fromEntity(final BITalinoFrame frame) {
    FrameDto dto = new FrameDto();
    dto.setAnalog(frame.getAnalog());
    dto.setDigital(frame.getDigital());
    dto.setCrc(frame.getCRC());
    dto.setSeq(frame.getSequence());
    return dto;
  }

  public static BITalinoFrame toEntity(final FrameDto dto) {
    BITalinoFrame frame = new BITalinoFrame();
    frame.setAnalog(dto.getAnalog());
    frame.setDigital(dto.getDigital());
    frame.setCRC(dto.getCrc());
    frame.setSequence(dto.getSeq());
    return frame;
  }

  @Override
  public String toString() {
    return "FrameDto [crc=" + crc + ", seq=" + seq + ", analog="
        + Arrays.toString(analog) + ", digital=" + Arrays.toString(digital)
        + "]";
  }

}