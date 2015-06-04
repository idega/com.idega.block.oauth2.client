package com.idega.block.oauth2.client.business.impl;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.idega.core.business.DefaultSpringBean;

@Service(GoogleAuthorizationService.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class GoogleAuthorizationService extends DefaultSpringBean {

	public static final String BEAN_NAME = "googleAuthorizationService";

	public static final String SERVICE_NAME = "google";

	public static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private Map<String, Credential> serverCredentials = new HashMap<String, Credential>();

	public Credential getServerAccountCredentials(
			String emailAddress,
			Collection<String> scopes,
			String pkPath) throws GeneralSecurityException, IOException {
		Credential credential = serverCredentials.get(emailAddress);
		if(credential == null){
			credential = new GoogleCredential.Builder()
			    .setTransport(GoogleNetHttpTransport.newTrustedTransport())
			    .setJsonFactory(JSON_FACTORY)
			    .setServiceAccountId(emailAddress)
			    .setServiceAccountPrivateKeyFromP12File(new File(pkPath))
			    .setServiceAccountScopes(scopes)
			    .build();
			serverCredentials.put(emailAddress, credential);
		}

		return credential;
	}

	public Credential getCredential(String emailAddress) {
		return serverCredentials.get(emailAddress);
	}
}
