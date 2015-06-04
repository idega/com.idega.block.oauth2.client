/**
 * @(#)GoogleDriveServiceImpl.java    1.0.0 12:09:16
 *
 * Idega Software hf. Source Code Licence Agreement x
 *
 * This agreement, made this 10th of February 2006 by and between 
 * Idega Software hf., a business formed and operating under laws 
 * of Iceland, having its principal place of business in Reykjavik, 
 * Iceland, hereinafter after referred to as "Manufacturer" and Agura 
 * IT hereinafter referred to as "Licensee".
 * 1.  License Grant: Upon completion of this agreement, the source 
 *     code that may be made available according to the documentation for 
 *     a particular software product (Software) from Manufacturer 
 *     (Source Code) shall be provided to Licensee, provided that 
 *     (1) funds have been received for payment of the License for Software and 
 *     (2) the appropriate License has been purchased as stated in the 
 *     documentation for Software. As used in this License Agreement, 
 *     Licensee shall also mean the individual using or installing 
 *     the source code together with any individual or entity, including 
 *     but not limited to your employer, on whose behalf you are acting 
 *     in using or installing the Source Code. By completing this agreement, 
 *     Licensee agrees to be bound by the terms and conditions of this Source 
 *     Code License Agreement. This Source Code License Agreement shall 
 *     be an extension of the Software License Agreement for the associated 
 *     product. No additional amendment or modification shall be made 
 *     to this Agreement except in writing signed by Licensee and 
 *     Manufacturer. This Agreement is effective indefinitely and once
 *     completed, cannot be terminated. Manufacturer hereby grants to 
 *     Licensee a non-transferable, worldwide license during the term of 
 *     this Agreement to use the Source Code for the associated product 
 *     purchased. In the event the Software License Agreement to the 
 *     associated product is terminated; (1) Licensee's rights to use 
 *     the Source Code are revoked and (2) Licensee shall destroy all 
 *     copies of the Source Code including any Source Code used in 
 *     Licensee's applications.
 * 2.  License Limitations
 *     2.1 Licensee may not resell, rent, lease or distribute the 
 *         Source Code alone, it shall only be distributed as a 
 *         compiled component of an application.
 *     2.2 Licensee shall protect and keep secure all Source Code 
 *         provided by this this Source Code License Agreement. 
 *         All Source Code provided by this Agreement that is used 
 *         with an application that is distributed or accessible outside
 *         Licensee's organization (including use from the Internet), 
 *         must be protected to the extent that it cannot be easily 
 *         extracted or decompiled.
 *     2.3 The Licensee shall not resell, rent, lease or distribute 
 *         the products created from the Source Code in any way that 
 *         would compete with Idega Software.
 *     2.4 Manufacturer's copyright notices may not be removed from 
 *         the Source Code.
 *     2.5 All modifications on the source code by Licencee must 
 *         be submitted to or provided to Manufacturer.
 * 3.  Copyright: Manufacturer's source code is copyrighted and contains 
 *     proprietary information. Licensee shall not distribute or 
 *     reveal the Source Code to anyone other than the software 
 *     developers of Licensee's organization. Licensee may be held 
 *     legally responsible for any infringement of intellectual property 
 *     rights that is caused or encouraged by Licensee's failure to abide 
 *     by the terms of this Agreement. Licensee may make copies of the 
 *     Source Code provided the copyright and trademark notices are 
 *     reproduced in their entirety on the copy. Manufacturer reserves 
 *     all rights not specifically granted to Licensee.
 *
 * 4.  Warranty & Risks: Although efforts have been made to assure that the 
 *     Source Code is correct, reliable, date compliant, and technically 
 *     accurate, the Source Code is licensed to Licensee as is and without 
 *     warranties as to performance of merchantability, fitness for a 
 *     particular purpose or use, or any other warranties whether 
 *     expressed or implied. Licensee's organization and all users 
 *     of the source code assume all risks when using it. The manufacturers, 
 *     distributors and resellers of the Source Code shall not be liable 
 *     for any consequential, incidental, punitive or special damages 
 *     arising out of the use of or inability to use the source code or 
 *     the provision of or failure to provide support services, even if we 
 *     have been advised of the possibility of such damages. In any case, 
 *     the entire liability under any provision of this agreement shall be 
 *     limited to the greater of the amount actually paid by Licensee for the 
 *     Software or 5.00 USD. No returns will be provided for the associated 
 *     License that was purchased to become eligible to receive the Source 
 *     Code after Licensee receives the source code. 
 */
package com.idega.block.oauth2.client.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.directwebremoting.annotations.Param;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.idega.block.oauth2.client.business.GoogleDriveService;
import com.idega.block.oauth2.client.presentation.bean.GoogleDriveObject;
import com.idega.core.business.DefaultSpringBean;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * <p>Implementation of {@link GoogleDriveService}</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 2015 June 3
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
/*
 * Spring
 */
@Service(GoogleDriveServiceImpl.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
//@EnableAspectJAutoProxy(proxyTargetClass=true)

/*
 * DWR
 */
@RemoteProxy(
	name=GoogleDriveServiceImpl.JAVASCRIPT_CLASS_NAME,
	creator=SpringCreator.class,
	creatorParams={
	@Param(
		name="beanName",
		value=GoogleDriveServiceImpl.BEAN_NAME),
	@Param(
		name="javascript",
		value=GoogleDriveServiceImpl.JAVASCRIPT_CLASS_NAME)
	}
)
public class GoogleDriveServiceImpl extends DefaultSpringBean implements
		GoogleDriveService {

	public static final String BEAN_NAME = "googleDriveService";

	public static final String JAVASCRIPT_CLASS_NAME = "GoogleDriveService";

	@Autowired
	private GoogleAuthorizationService googleAuthorizationService;

	protected GoogleAuthorizationService getGoogleAuthorizationService() {
		if (this.googleAuthorizationService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.googleAuthorizationService;
	}

	/* (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getDrive(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public Drive getDrive(
			String applicationName, 
			Credential credentials) {
		if (!StringUtil.isEmpty(applicationName)) {
			return new Drive.Builder(
					new NetHttpTransport(), 
					new JacksonFactory(), 
					credentials).setApplicationName(applicationName).build();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getFiles(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public Files getFiles(
			String applicationName, 
			Credential credentials) {
		Drive drive = getDrive(applicationName, credentials);
		if (drive != null) {
			return drive.files();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getFilesList(java.lang.String, com.google.api.client.auth.oauth2.Credential, java.lang.String)
	 */
	@Override
	public FileList getFilesList(
			String applicationName, 
			Credential credentials, 
			String query) {
		
//		Client c = new Client();
//		Object o = c.resource("https://www.googleapis.com/drive/v2/files?corpus=DEFAULT&key=").header(name, value).get(Object.class);
		
		Files applicationService = getFiles(applicationName, credentials);
		if (applicationService != null) {
			try {
				if (!StringUtil.isEmpty(query)) {
					 
					return applicationService.list().setCorpus("DEFAULT").setQ(query.toString()).execute();
				} else {
//					File file = new File();
//					  file.setTitle("Title2");
//					  file.setDescription("Description2");
//					  file.setMimeType("application/vnd.google-apps.drive-sdk");
//					applicationService.insert(file).execute();
					return applicationService.list().setCorpus("DEFAULT").execute();
				}
			} catch (IOException e) {
				getLogger().log(Level.WARNING, 
						"Failed to get document by query: " + query + 
						" cause of: ", e);
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getFiles(java.lang.String, com.google.api.client.auth.oauth2.Credential, java.lang.String)
	 */
	@Override
	public List<File> getFiles(
			String applicationName, 
			Credential credentials, 
			String query) {
		FileList list = getFilesList(applicationName, credentials, query);
		if (list != null) {
			return list.getItems();
		}

		return Collections.emptyList();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getConverted(java.util.List)
	 */
	@Override
	public List<GoogleDriveObject> getConverted(List<File> files) {
		ArrayList<GoogleDriveObject> convertedList = new ArrayList<GoogleDriveObject>();
		
		if (!ListUtil.isEmpty(files)) {
			for (File file : files) {
				convertedList.add(new GoogleDriveObject(file));
			}
		}

		return convertedList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.GoogleDriveService#getFiles(java.lang.String, java.lang.String, java.lang.String)
	 */
	@RemoteMethod
	@Override
	public List<GoogleDriveObject> getFiles(
			String applicationName, 
			String email, 
			String query) {
		Credential credential = getGoogleAuthorizationService().getCredential(email);
		if (credential != null) {
			return getConverted(getFiles(applicationName, credential, query));
		} else {
			getLogger().log(Level.WARNING, "Credentials not found by email: " + email);
		}

		return Collections.emptyList();
	}

	/*
	 * 
	 */
	@RemoteMethod
	@Override
	public List<GoogleDriveObject> getDirectoryChildren(
			String applicationName, 
			String email,
			String parentId) {
		StringBuilder query = new StringBuilder();
		query.append("'").append(parentId).append("' in parents ")
		.append("and trashed = false ")
		.append("and hidden = false ");

		return getFiles(applicationName, email, query.toString());
	}
}
