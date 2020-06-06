package com.wiki.qa.tests;

import com.wiki.qa.manager.AppManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.net.MalformedURLException;

public class TestBase {
   protected static AppManager app = new AppManager();


    @BeforeSuite
    public void setUp() throws MalformedURLException {
        app.init();

    }

    @AfterSuite(enabled = false)
    public  void tearDown(){
        app.stop();
    }

}
