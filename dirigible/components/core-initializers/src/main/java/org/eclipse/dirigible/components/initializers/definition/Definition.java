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
package org.eclipse.dirigible.components.initializers.definition;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.springframework.data.annotation.Transient;

import com.google.gson.annotations.Expose;

/**
 * The Class Extension.
 */
@Entity
@Table(name = "DIRIGIBLE_DEFINITIONS")
public class Definition extends Artefact {
	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEFINITION_ID", nullable = false)
	private Long id;
	
	/** The checksum. */
	@Column(name = "DEFINITION_CHECKSUM", columnDefinition = "VARCHAR", nullable = true, length = 32)
	@Expose
    protected String checksum;
	
	/** The status. */
	@Column(name = "DEFINITION_STATE", columnDefinition = "VARCHAR", nullable = true, length = 32)
	@Expose
    protected String state;
	
	/** The status. */
	@Column(name = "DEFINITION_MESSAGE", columnDefinition = "VARCHAR", nullable = true, length = 2000)
	@Expose
    protected String message;
	
	/** The content. */
	@Transient
	private transient byte[] content;

	/**
	 * Instantiates a new definition.
	 *
	 * @param location the location
	 * @param name the name
	 * @param type the type
	 * @param content the content
	 */
	public Definition(String location, String name, String type, byte[] content) {
		super(location, name, type, null, null);
		this.content = content;
		updateChecksum(content);
	}
	
	/**
	 * Instantiates a new definition.
	 */
	public Definition() {
		super();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the checksum.
	 *
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * Sets the checksum.
	 *
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Update checksum.
	 *
	 * @param content the content
	 */
	public void updateChecksum(byte[] content) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(content);
		    byte[] digest = md.digest();
		    this.checksum = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			this.checksum = "";
		}
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}
	
	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Definition [id=" + id + ", location=" + location + ", name=" + name + ", description=" + description
				+ ", type=" + type + ", key=" + key + ", checksum=" + checksum + ", dependencies=" + dependencies
				+ ", state=" + state + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy="
				+ updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
}
