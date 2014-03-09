package com.bitalino.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BITALINO_FRAMES")
public class BITalinoFrame {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private int crc;
  private int seq;
  private int[] analog = new int[6];
  private int[] digital = new int[4];

  public BITalinoFrame() {
  }

  public int getCRC() {
    return crc;
  }

  public void setCRC(int cRC) {
    crc = cRC;
  }

  public int getSequence() {
    return seq;
  }

  public void setSequence(int seq) {
    this.seq = seq;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getCrc() {
    return crc;
  }

  public void setCrc(int crc) {
    this.crc = crc;
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

}