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
package org.eclipse.dirigible.components.extensions.synchronizer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.eclipse.dirigible.components.base.artefact.ArtefactState;
import org.eclipse.dirigible.components.base.artefact.topology.TopologicalDepleter;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.helpers.JsonHelper;
import org.eclipse.dirigible.components.base.synchronizer.Synchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.extensions.domain.Extension;
import org.eclipse.dirigible.components.extensions.service.ExtensionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The Class ExtensionsSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(20)
public class ExtensionsSynchronizer<A extends Artefact> implements Synchronizer<Extension> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ExtensionsSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_EXTENSION. */
	public static final String FILE_EXTENSION_EXTENSION = ".extension";
	
	/** The extension service. */
	private ExtensionService extensionService;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new extensions synchronizer.
	 *
	 * @param extensionService the extension service
	 */
	@Autowired
	public ExtensionsSynchronizer(ExtensionService extensionService) {
		this.extensionService = extensionService;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<Extension> getService() {
		return extensionService;
	}


	/**
	 * Checks if is accepted.
	 *
	 * @param file the file
	 * @param attrs the attrs
	 * @return true, if is accepted
	 */
	@Override
	public boolean isAccepted(Path file, BasicFileAttributes attrs) {
		return file.toString().endsWith(getFileExtension());
	}
	
	/**
	 * Checks if is accepted.
	 *
	 * @param type the artefact
	 * @return true, if is accepted
	 */
	@Override
	public boolean isAccepted(String type) {
		return Extension.ARTEFACT_TYPE.equals(type);
	}

	/**
	 * Load.
	 *
	 * @param location the location
	 * @param content the content
	 * @return the list
	 */
	@Override
	public List<Extension> load(String location, byte[] content) {
		Extension extension = JsonHelper.fromJson(new String(content, StandardCharsets.UTF_8), Extension.class);
		Configuration.configureObject(extension);
		extension.setLocation(location);
		extension.setName("");
		extension.setType(Extension.ARTEFACT_TYPE);
		extension.updateKey();
		try {
			Extension maybe = getService().findByKey(extension.getKey());
			if (maybe != null) {
				extension.setId(maybe.getId());
			}
			getService().save(extension);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("extension: {}", extension);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
		}
		return List.of(extension);
	}
	
	/**
	 * Prepare.
	 *
	 * @param wrappers the wrappers
	 * @param depleter the depleter
	 */
	@Override
	public void prepare(List<TopologyWrapper<? extends Artefact>> wrappers, TopologicalDepleter<TopologyWrapper<? extends Artefact>> depleter) {
	}
	
	/**
	 * Process.
	 *
	 * @param wrappers the wrappers
	 * @param depleter the depleter
	 */
	@Override
	public void process(List<TopologyWrapper<? extends Artefact>> wrappers, TopologicalDepleter<TopologyWrapper<? extends Artefact>> depleter) {
		try {
			List<TopologyWrapper<? extends Artefact>> results = depleter.deplete(wrappers, ArtefactLifecycle.CREATED.toString());
			callback.registerErrors(this, results, ArtefactLifecycle.CREATED.toString(), ArtefactState.FAILED_CREATE_UPDATE);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
		}
	}
	
	/**
	 * Complete.
	 *
	 * @param wrapper the wrapper
	 * @param flow the flow
	 * @return true, if successful
	 */
	@Override
	public boolean complete(TopologyWrapper<Artefact> wrapper, String flow) {
		callback.registerState(this, wrapper, ArtefactLifecycle.CREATED.toString(), ArtefactState.SUCCESSFUL_CREATE_UPDATE);
		return true;
	}

	/**
	 * Cleanup.
	 *
	 * @param extension the extension
	 */
	@Override
	public void cleanup(Extension extension) {
		try {
			getService().delete(extension);
			callback.registerState(this, extension, ArtefactLifecycle.DELETED.toString(), ArtefactState.SUCCESSFUL_DELETE);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, extension, ArtefactLifecycle.DELETED.toString(), ArtefactState.FAILED_DELETE);
		}
	}
	
	/**
	 * Sets the callback.
	 *
	 * @param callback the new callback
	 */
	@Override
	public void setCallback(SynchronizerCallback callback) {
		this.callback = callback;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_EXTENSION;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return Extension.ARTEFACT_TYPE;
	}

}
