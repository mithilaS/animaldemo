package com.mithi.animaldemo;

import com.mithi.animaldemo.worker.AnimalWorker;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.camunda.community.process_test_coverage.junit5.platform8.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;

@SpringBootTest
@ZeebeSpringTest
@ExtendWith(ProcessEngineCoverageExtension.class)
public class TestAnimalDemo {

    @Autowired
    private ZeebeClient zeebe;

    @Autowired private ZeebeTestEngine zeebeTestEngine;

    @MockBean private AnimalWorker animalWorker;

    private static final Duration DURATION = Duration.ofMillis(50000L);

   // @Test
    public void testAnimalDemoProcess() throws Exception {


        Map<String, String> variables = new HashMap<>();
        variables.put("animalName", "bears");
        // start a process instance
        ProcessInstanceEvent processInstance =
                zeebe
                        .newCreateInstanceCommand() //
                        .bpmnProcessId("animal-picture-bpmn")
                        .latestVersion() //
                        .variables(variables) //
                        .send()
                        .join();


        // Now the process should run to the end
        waitForProcessInstanceCompleted(processInstance, DURATION);

         assertThat(processInstance)
                .hasPassedElement("fetch-animal")
                 .isCompleted();

    }
}
