/**
 * @(#)OAuth2LoginServiceImpl.java    1.0.0 1:33:33 PM
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.oauth2.client.OAuth2LoginConstants;
import com.idega.block.oauth2.client.business.OAuth2LoginService;
import com.idega.block.oauth2.client.data.OAuth2CredentialEntity;
import com.idega.block.oauth2.client.data.dao.OAuth2CredentialEntityDAO;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.core.accesscontrol.business.LoginBusinessBean;
import com.idega.core.business.DefaultSpringBean;
import com.idega.presentation.IWContext;
import com.idega.user.bean.UserDataBean;
import com.idega.user.business.UserApplicationEngine;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.Group;
import com.idega.user.data.User;
import com.idega.util.CoreUtil;
import com.idega.util.ListUtil;
import com.idega.util.StringUtil;
import com.idega.util.expression.ELUtil;

/**
 * @see OAuth2LoginService
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Jul 1, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Service(OAuth2LoginService.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OAuth2LoginServiceImpl extends DefaultSpringBean implements
		OAuth2LoginService {

	private com.idega.user.business.UserBusiness userBusiness = null;

	@Autowired
	private OAuth2UsersGroupBean oAuth2UsersGroupBean;
	
	private UserApplicationEngine userApplicationEngine;
	
	@Autowired
	private OAuth2CredentialEntityDAO oAuth2CredentialEntityDAO;
	
	@Override
	public URI getAuthorizationPageURI(URI endpointURL, URI redirectURL, 
			String applicationId, String applicationSecret) {
		OAuthClientRequest request = getAuthorizationRequest(endpointURL, 
				redirectURL, applicationId, applicationSecret);
		if (request == null) {
			return null;
		}
		
		try {
			return new URI(request.getLocationUri());
		} catch (URISyntaxException e) {
			getLogger().log(Level.WARNING, "Failed to form URI from: " + request.getLocationUri());
		}
		
		return null;
	}
	
	public OAuthClientRequest getAuthorizationRequest(URI endpointURL, 
			URI redirectURL, String applicationId, String applicationSecret) {
		OAuthClientRequest request = null;
		try {
			request = OAuthClientRequest
			        .tokenLocation(endpointURL.toString())
			        .setClientId(applicationId)
			        .setRedirectURI(OAuth2LoginConstants.REDIRECT_URI)
			        .setGrantType(GrantType.AUTHORIZATION_CODE)
			        .buildBodyMessage();
		} catch (OAuthSystemException e) {
			getLogger().log(Level.WARNING, "Failed to create request for " +
					"OAuth 2.0 login, cause of: ", e);
		}
		
		return request;
	}
	
	public OAuthAccessTokenResponse getOAuthAccessTokenResponse(URI endpointURL, 
			URI redirectURL, String applicationId, String applicationSecret,
			Class<? extends OAuthAccessTokenResponse> responseType) {
		OAuthClientRequest request = getAuthorizationRequest(endpointURL, 
				redirectURL, applicationId, applicationSecret);
		if (request == null) {
			return null;
		}
		
		OAuthClient client = new OAuthClient(new URLConnectionClient());
		
		try {
			return client.accessToken(request, responseType);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed to get access token from " +
					"server " + endpointURL.toString() + " cause of: " , e);
		}
		
		return null;
	}
	
	@Override
	public String getAccessToken(URI endpointURL, 
			URI redirectURL, String applicationId, String applicationSecret,
			Class<? extends OAuthAccessTokenResponse> responseType) {
		
		OAuthAccessTokenResponse oauthResponse = getOAuthAccessTokenResponse(
				endpointURL, redirectURL, applicationId, applicationSecret, responseType);
		if (oauthResponse == null) {
			return null;
		}
		
        return oauthResponse.getAccessToken();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.OAuth2LoginService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.util.Date, java.lang.String)
	 */
	@Override
	public User update(String serviceUserId, String serviceName,
			String email, String firstName, String middleName, 
			String lastName, String displayname, String description, 
			Integer gender, Date dateOfBirth, String fullName, 
			String accessToken, String refreshToken, Long expiresIn) {
		
		User user = null;
		
		// Searching if external user already registered or user with given mail
		OAuth2CredentialEntity credentialEntity = getOAuth2CredentialEntityDAO()
				.findByServiceUserIdAndProvider(serviceUserId, serviceName);
		if (credentialEntity != null) {
			user = credentialEntity.getIdegaUser();
		} else {
			user = getUser(email);
		}

		// Creating user, if does not exits
		if (user == null && !StringUtil.isEmpty(email)) {
			UserDataBean userData = new UserDataBean();
			userData.setAccountEnabled(Boolean.TRUE);
			userData.setAccountExists(Boolean.TRUE);
			userData.setName(fullName);
			userData.setEmail(email);
			userData.setChangePasswordNextTime(Boolean.TRUE);
			userData.setJuridicalPerson(Boolean.FALSE);
			
			Group oauth2Group = getOAuth2UsersGroupBean().getGroup();
			getUserApplicationEngine().createUser(userData, 
					Integer.valueOf(oauth2Group.getId()), 
					new ArrayList<Integer>(), null, Boolean.FALSE, Boolean.TRUE, 
					email, null);
		}

		if (user == null) {
			return null;
		}
		
		credentialEntity = getOAuth2CredentialEntityDAO().update(
				credentialEntity != null ? credentialEntity.getId() : null, 
				expiresIn, refreshToken, accessToken, 
				user, serviceUserId, serviceName);
		if (credentialEntity == null || credentialEntity.getId() == null) {
			getLogger().warning("Failed to update credentials of idega user!");
			return null;
		}

		return user;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.OAuth2LoginService#login(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public boolean login(String serviceUserId, String serviceName, 
			String accessToken, String refreshToken, Long expiresIn) {
		if (StringUtil.isEmpty(serviceName) || StringUtil.isEmpty(serviceUserId)) {
			return Boolean.FALSE;
		}
		
		/* We need update tokens and expiration time! 
		 * Works only with existing ones.*/
		OAuth2CredentialEntity credentialEntity = getOAuth2CredentialEntityDAO()
				.update(null, expiresIn, refreshToken, accessToken, null, serviceUserId, serviceName);
		if (credentialEntity == null) {
			return Boolean.FALSE;
		}
		
		return login(credentialEntity.getIdegaUser());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.OAuth2LoginService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean login(String serviceUserId, String serviceName) {
		return login(serviceUserId, serviceName, null, null, null);
	}
	
	/**
	 * 
	 * <p>Tries to login {@link User} to Idega system.</p>
	 * @param user to login, not <code>null</code>;
	 * @return <code>true</code> if user logged in, <code>false</code>
	 * otherwise.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	protected boolean login(User user) {
		if (user == null) {
			return Boolean.FALSE;
		}
		
		IWContext iwc = CoreUtil.getIWContext();
		if (iwc == null) {
			return Boolean.FALSE;
		}
		
		// XXX: What bad this could do? 
		if (iwc.isLoggedOn()) {
			return Boolean.TRUE;
		}
		
		try {
			LoginBusinessBean.getDefaultLoginBusinessBean().logInUser(iwc, user);
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Failed to login, cause of: ", e);
			return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.OAuth2LoginService#getUserByServiceId(java.lang.String)
	 */
	@Override
	public User getUser(String userServiceId, String serviceName) {
		if (StringUtil.isEmpty(userServiceId) || StringUtil.isEmpty(serviceName)) {
			return null;
		}
		
		OAuth2CredentialEntity credential = getOAuth2CredentialEntityDAO()
				.findByServiceUserIdAndProvider(userServiceId, serviceName);
		if (credential == null) {
			return null;
		}
		
		return credential.getIdegaUser();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.mobile.business.ExternalLoginService#getIdegaUser(java.lang.String, java.lang.String)
	 */
	@Override
	public User getUser(String email) {
		if (StringUtil.isEmpty(email)) {
			return null;
		}
		
		Collection<com.idega.user.data.User> users = getUsers(email);
		if (ListUtil.isEmpty(users)) {
			return null;
		}

		return users.iterator().next();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.OAuth2LoginService#getUsers(java.lang.String)
	 */
	@Override
	public Collection<User> getUsers(String email) {
		if (StringUtil.isEmpty(email)) {
			return null;
		}
		
		return getUserBusiness().getUsersByEmail(email);
	}
	
	protected com.idega.user.business.UserBusiness getUserBusiness() {
		if (this.userBusiness != null) {
			return this.userBusiness;
		}
		
		try {
			this.userBusiness = IBOLookup.getServiceInstance(
					CoreUtil.getIWContext(), UserBusiness.class);
		} catch (IBOLookupException e) {
			getLogger().log(Level.WARNING, 
					"Failed to get " + UserBusiness.class + ", cause of: ", e);
		}
		
		return this.userBusiness;
	}
	
	protected OAuth2CredentialEntityDAO getOAuth2CredentialEntityDAO() {
		if (this.oAuth2CredentialEntityDAO == null) {
			ELUtil.getInstance().autowire(this);
		}
		
		return this.oAuth2CredentialEntityDAO;
	}
	
	protected UserApplicationEngine getUserApplicationEngine() {
		if (this.userApplicationEngine == null) {
			this.userApplicationEngine = ELUtil.getInstance()
					.getBean(UserApplicationEngine.class);
		}
		
		return this.userApplicationEngine;
	}
	
	protected OAuth2UsersGroupBean getOAuth2UsersGroupBean() {
		if (this.oAuth2UsersGroupBean == null) {
			ELUtil.getInstance().autowire(this);
		}
		
		return this.oAuth2UsersGroupBean;
	}
}
