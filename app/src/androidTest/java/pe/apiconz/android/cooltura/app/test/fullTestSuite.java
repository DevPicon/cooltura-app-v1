package pe.apiconz.android.cooltura.app.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/**
 * Created by Indra on 04/01/2015.
 */
public class FullTestSuite {
    public static Test suite(){
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public FullTestSuite(){
        super();
    }
}
