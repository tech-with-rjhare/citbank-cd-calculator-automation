package com.cit.listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class listner implements ITestListener {

    public void onTestStart(ITestResult result) {
        System.out.println("test started...");
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("test passed...");
    }

    public void onTestFailure(ITestResult result) {
        System.out.println("test failed...");
    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("test skipped...");
    }

    public void onStart(ITestContext context) {
        System.out.println("Test execution is started.......");
    }

    public void onFinish(ITestContext context) {
        System.out.println("test execution is completed...");
    }
}
