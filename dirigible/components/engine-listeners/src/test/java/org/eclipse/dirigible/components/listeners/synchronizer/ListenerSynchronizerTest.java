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
package org.eclipse.dirigible.components.listeners.synchronizer;

import org.eclipse.dirigible.components.listeners.domain.Listener;
import org.eclipse.dirigible.components.listeners.repository.ListenerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components" })
@EntityScan("org.eclipse.dirigible.components")
public class ListenerSynchronizerTest {
    @Autowired
    private ListenerSynchronizer listenerSynchronizer;

    @Autowired
    private ListenerRepository listenerRepository;

    @AfterEach
    public void cleanup() {
        listenerRepository.deleteAll();
    }

    @Test
    public void isAcceptedPath() {
        assertTrue(listenerSynchronizer.isAccepted(Path.of("/a/b/c/l1.listener"), null));
    }

    @Test
    public void isAcceptedArtefact() {
        assertTrue(listenerSynchronizer.isAccepted(new Listener("/a/b/c/l1.listener", "name1", "description", "handler1", 'Q').getType()));
    }

    @Test
    public void load() {
        String content = "{\"location\":\"/control/control.listener\",\"name\":\"/control/control\",\"kind\":\"Q\",\"handler\":\"control/handler.js\",\"description\":\"Control Listener\",\"createdBy\":\"system\",\"createdAt\":\"2017-07-06T2:53:01+0000\"}";
        List<Listener> list = listenerSynchronizer.load("/test/test.listener", content.getBytes());
        assertNotNull(list);
        assertEquals("/test/test.listener", list.get(0).getLocation());
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}
