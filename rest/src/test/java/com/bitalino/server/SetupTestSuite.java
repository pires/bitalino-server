/**
 * Copyright (C) Ubiwhere, Lda.
 * 2013
 *
 * The reproduction, transmission or use of this document or its contents is not
 * permitted without express written authorization. All rights, including rights
 * created by patent grant or registration of a utility model or design, are
 * reserved. Modifications made to this document are restricted to authorized
 * personnel only. Technical specifications and features are binding only when
 * specifically and expressly agreed upon in a written contract.
 */
package com.bitalino.server;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Testing environment bootstap.
 */
public class SetupTestSuite {

  private static final Logger logger = LoggerFactory
      .getLogger(SetupTestSuite.class);

  public final static int SERVER_PORT = 8181;
  private GlassFish gfServer;

  /**
   * This is meant to run before all tests are performed.
   *
   * @throws Throwable
   */
  @BeforeSuite
  public final void setUp() {
    try {
      // set-up embedded Glassfish instance
      GlassFishProperties gfProperties = new GlassFishProperties();
      final String domainXmlPath = new File(
          "target/test-classes/glassfish-domain.xml").toURI().toString();
      logger.info("Glassfish domain configuration loaded from: '{}'",
          domainXmlPath);
      gfProperties.setConfigFileURI(domainXmlPath);
      gfServer = GlassFishRuntime.bootstrap().newGlassFish(gfProperties);
      gfServer.start();

      CommandResult res;
      // create a JDBC resource
      // for some unknown reason the creation of this resource via domain.xml
      // messes up the tests!
      res = gfServer.getCommandRunner().run("create-jdbc-resource",
          "--connectionpoolid", "MyPool", "jdbc/DS");
      logger.info(res.getOutput());
      // create WAR
      ScatteredArchive archive = new ScatteredArchive("bitalino",
          ScatteredArchive.Type.WAR);
      /*
       * by adding individual files and folders, we keep control of what is and
       * what isn't deployed for testing. for instance, we want to ignore EJB
       * timers, as they blow up :-/
       */
      archive.addClassPath(new File("../model/target", "classes/com"));
      archive.addClassPath(new File("target", "classes"));
      archive.addClassPath(new File("target", "test-classes"));
      archive.addMetadata(new File("src/main/webapp/WEB-INF", "web.xml"));

      // deploy the scattered web archive.
      deploy(archive.toURI());
    } catch (Exception e) {
      logger.error(
          "There was an error while setting up the testing environment.", e);
    }
  }

  /**
   * @throws GlassFishException
   *           This is meant to run after all tests are performed.
   *
   * @throws
   */
  @AfterSuite
  public final void tearDown() {
    try {
      gfServer.stop();
      gfServer.dispose();
    } catch (Exception e) {
      logger.error("There was an error while shutting down embedded Glassfish");
    }
  }

  /**
   * Persists test scenario.
   *
   * @throws GlassFishException
   * @throws IOException
   */
  private final void deploy(URI uri) throws GlassFishException, IOException {
    logger.info("Deploying webapp..");
    gfServer.getDeployer().deploy(uri, "--contextroot=bitalino");
  }

}