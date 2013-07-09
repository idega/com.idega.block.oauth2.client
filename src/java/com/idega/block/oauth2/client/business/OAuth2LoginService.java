/**
 * @(#)OAuth2LoginService.java    1.0.0 1:18:09 PM
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
package com.idega.block.oauth2.client.business;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;

import com.idega.block.oauth2.client.OAuth2LoginConstants;
import com.idega.block.oauth2.client.data.OAuth2CredentialEntity;
import com.idega.user.data.User;

/**
 * <p>Service for logging in external services like facebook.com, google,
 * github or etc., which support OAuth2 specification.</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Jul 1, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface OAuth2LoginService {

	public static final String BEAN_NAME = "oAuth2LoginService";
	
	/**
	 * 
	 * <p>TODO Not working correctly yet</p>
	 * @param endpointURL
	 * @param clientId
	 * @param responseType
	 * @return
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public String getAccessToken(URI endpointURL, 
			URI redirectURL, String applicationId, String applicationSecret,
			Class<? extends OAuthAccessTokenResponse> responseType);

	/**
	 * 
	 * <p>TODO Not working correctly yet</p>
	 * @param endpointURL
	 * @param applicationId
	 * @param redirectURL
	 * @param applicationSecret TODO
	 * @return
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public URI getAuthorizationPageURI(URI endpointURL,
			URI redirectURL, String applicationId, String applicationSecret);
	
	/**
	 * 
	 * @param email of {@link User} to find, not <code>null</code>;
	 * @return first occurrence of {@link User}, who has given e-mail or 
	 * <code>null</code> on failure; 
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public com.idega.user.data.User getUser(String email);

	/**
	 * 
	 * @param email to search idega {@link User}s by, not <code>null</code>;
	 * @return user, who has given e-mail or <code>null</code> on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public Collection<User> getUsers(String email);

	/**
	 * 
	 * <p>Checks if {@link User} by given service user id is registered in 
	 * database.</p>
	 * @param userServiceId to search records for, not <code>null</code>;
	 * @param serviceName it is google, facebook or etc., not <code>null</code>;
	 * @return external {@link User} registered in idega system or 
	 * <code>null</code> if external user not registered yet. 
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public User getUser(String userServiceId, String serviceName);

	/**
	 * 
	 * <p>Updates or persists OAuth 2.0 login data for {@link User}, 
	 * who has given email or will create new Idega {@link User} by data found 
	 * in service.</p>
	 * @param userServiceId is {@link User} id in selected service, 
	 * not <code>null</code>;
	 * @param serviceName is name of service, 
	 * defined in {@link OAuth2LoginConstants},
	 * for example: "google, facebook", not <code>null</code>;
	 * @param email which is user in service and locally;
	 * @param firstName is {@link User#getFirstName()};
	 * @param middleName is {@link User#getMiddleName()};
	 * @param lastName is {@link User#getLastName()};
	 * @param displayname is {@link User#getDisplayName()};
	 * @param description is {@link User#getDescription()};
	 * @param gender is {@link User#getGender()};
	 * @param dateOfBirth is data, when user was born;
	 * @param fullName is {@link User#getName()};
	 * @param accessToken is code for accessing data of {@link User}
	 * in selected service;
	 * @param refreshToken is code for refreshing accessToken;
	 * @param expiresIn is time when accessToke will expire;
	 * @return updated/persisted Idega {@link User} or <code>null</code>
	 * on failure;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public User update(String userServiceId, String serviceName, String email,
			String firstName, String middleName, String lastName,
			String displayname, String description, Integer gender,
			Date dateOfBirth, String fullName, String accessToken, String refreshToken, Long expiresIn);

	/**
	 * 
	 * <p>Tries to login {@link User}, if registered in 
	 * {@link OAuth2CredentialEntity}.</p>
	 * @param serviceUserId is user id in external system, for example, 
	 * facebook.com id. It is not your email, it is id, 
	 * which facebook.com generated! Not <code>null</code>;
	 * @param serviceName is name on {@link OAuth2LoginConstants}, 
	 * for example: "facebook", not <code>null</code>;
	 * @return true if {@link User} logged in, <code>false</code> 
	 * otherwise.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public boolean login(String serviceUserId, String serviceName);

	/**
	 * 
	 * <p>Tries to login {@link User}, if registered in 
	 * {@link OAuth2CredentialEntity}.</p>
	 * @param serviceUserId is user id in external system, for example, 
	 * facebook.com id. It is not your email, it is id, 
	 * which facebook.com generated! Not <code>null</code>;
	 * @param serviceName is name on {@link OAuth2LoginConstants}, 
	 * for example: "facebook", not <code>null</code>;
	 * @param accessToken it is key, that service provider given to access
	 * provided services;
	 * @param refreshToken it is key for getting new access token;
	 * @param expiresIn it is time in which access token expires;
	 * @return <code>true</code> when logged in to Idega system, 
	 * <code>false</code> otherwise;
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public boolean login(String serviceUserId, String serviceName, String accessToken,
			String refreshToken, Long expiresIn);
}
