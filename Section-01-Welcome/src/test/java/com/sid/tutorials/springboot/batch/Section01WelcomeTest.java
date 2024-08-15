package com.sid.tutorials.springboot.batch;

import com.sid.tutorials.springboot.batch.bean.Person;
import com.sid.tutorials.springboot.batch.mockdata.MockDataPrep;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kunmu On 15-08-2024
 */
@SpringBootTest(classes = Section01Welcome.class)
@SpringBatchTest
@Import(MyTestConfigClass.class)
class Section01WelcomeTest {

    @Autowired
    private MockDataPrep mockDataPrep;

    @Autowired
    @Qualifier("customJoblauncherTestutils")
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier("customJoblauncherMasterTestutils")
    private JobLauncherTestUtils customJoblauncherMasterTestutils;

    @Ignore
    @Test
    void getPeople() {
        try {
            List<Person> people = mockDataPrep.getPeople();
            people.stream().forEach(p -> System.out.println(p.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Ignore
    @Test
    void test() throws Exception {
        JobExecution launchJob = jobLauncherTestUtils.launchJob();
        assertEquals("Car-Job", launchJob.getJobInstance().getJobName());
        assertEquals(ExitStatus.COMPLETED, launchJob.getExitStatus());
    }

    @Test
    void testMasterJob() throws Exception {
        JobExecution launchJob = customJoblauncherMasterTestutils.launchJob();
        assertEquals("Car-Master-Job", launchJob.getJobInstance().getJobName());
        assertEquals(ExitStatus.COMPLETED, launchJob.getExitStatus());
    }

}