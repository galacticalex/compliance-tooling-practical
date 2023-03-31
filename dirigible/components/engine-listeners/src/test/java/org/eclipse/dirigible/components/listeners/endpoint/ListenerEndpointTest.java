/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.components.listeners.endpoint;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.eclipse.dirigible.components.listeners.domain.Listener;
import org.eclipse.dirigible.components.listeners.repository.ListenerRepository;
import org.eclipse.dirigible.components.listeners.service.ListenerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"org.eclipse.dirigible.components"})
@Transactional
public class ListenerEndpointTest {

    @Autowired
    private ListenerService listenerService;

    @Autowired
    private ListenerRepository listenerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @BeforeEach
    public void setup() {
    	
    	cleanup();
    	
        listenerService.save(new Listener("/a/b/c/l1.listener", "name1", "description", "handler1", 'Q'));
        listenerService.save(new Listener("/a/b/c/l2.listener", "name2", "description", "handler2", 'Q'));
        listenerService.save(new Listener("/a/b/c/l3.listener", "name3", "description", "handler3", 'Q'));
    }

    @AfterEach
    public void cleanup() {
        listenerRepository.deleteAll();
    }

    @Test
    public void findAllExtensionPoints() throws Exception {
        mockMvc.perform(get("/services/listeners"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
                //.andExpect(jsonPath("$.content[0].location").value("/a/b/c/l1.listener"));
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}
