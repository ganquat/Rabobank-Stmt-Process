package com.robobank.statement;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

/**
 * Created by Ganesh_C01 on 7/14/2018.
 */
public class CustStmtServiceTest {

    MockMultipartFile file;

    InputStream validFile;

    @Before
    public void setup() {
        validFile = ClassLoader.getSystemResourceAsStream("records.xml");
    }

    @Test
    public void convertFileDataToCustStmtModelValidTest(){

    }

    @Test
    public void checkForInvalidTransactionValidTest(){

    }
}
