package com.idega.block.oauth2.client.bean;

import java.util.Collection;

public interface APICredentials {

	public String getName();

	public void setName(String name);

	public abstract String getFolderId();

	public abstract String getEmail();

	public abstract String getId();

	public abstract String getCertificatePath();

	public abstract Collection<String> getScopes();

	public abstract String getAppName();

}