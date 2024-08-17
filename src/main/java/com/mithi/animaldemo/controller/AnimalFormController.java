package com.mithi.animaldemo.controller;

import com.mithi.animaldemo.service.ProcessConstants;
import io.camunda.zeebe.client.ZeebeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class AnimalFormController {

    private static final Logger log = LoggerFactory.getLogger(AnimalFormController.class);

    @Autowired
    private ZeebeClient zeebe;

    @PostMapping("/start")
    public void startProcessInstance(@RequestBody Map<String, String> variables) {

        log.info(
                "Starting process `" + ProcessConstants.BPMN_PROCESS_ID + "` with variables: " + variables);

        var event = zeebe
                .newCreateInstanceCommand()
                .bpmnProcessId(ProcessConstants.BPMN_PROCESS_ID)
                .latestVersion()
                .variables(variables)

                .send().join();

        log.info("started a process instance: {}", event.getProcessInstanceKey() + " - " + event.getBpmnProcessId());
    }
}
