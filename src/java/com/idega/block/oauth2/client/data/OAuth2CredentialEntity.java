/**
 * @(#)GoogleCredentialEntity.java    1.0.0 10:52:46 AM
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
package com.idega.block.oauth2.client.data;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;
import com.idega.util.CoreConstants;
import com.idega.util.CoreUtil;

/**
 * <p>Entity for saving Google tokens.</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Jun 25, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Entity
@Table(
		name = OAuth2CredentialEntity.TABLE_NAME,
		uniqueConstraints = @UniqueConstraint(
				columnNames = {
						OAuth2CredentialEntity.COLUMN_SERVICE_USER_ID, 
						OAuth2CredentialEntity.COLUMN_SERVICE_PROVIDER_NAME, 
						OAuth2CredentialEntity.COLUMN_IDEGA_USER}))
@NamedQueries({
	@NamedQuery(
			name = OAuth2CredentialEntity.QUERY_FIND_ALL,
			query = "FROM OAuth2CredentialEntity g"
			),
	@NamedQuery(
			name = OAuth2CredentialEntity.QUERY_FIND_BY_ID,
			query = "FROM OAuth2CredentialEntity g " +
					"WHERE g.id =:" + OAuth2CredentialEntity.idProp
			),
	@NamedQuery(
			name = OAuth2CredentialEntity.QUERY_FIND_BY_SERVICE_USER_ID,
			query = "FROM OAuth2CredentialEntity g " +
					"WHERE g.serviceUserId =:" + OAuth2CredentialEntity.serviceUserIdProp
			),
	@NamedQuery(
			name = OAuth2CredentialEntity.QUERY_FIND_BY_SERVICE_USER_ID_AND_PROVIDER,
			query = "FROM OAuth2CredentialEntity g " +
					"WHERE g.serviceUserId =:" + OAuth2CredentialEntity.serviceUserIdProp + 
					CoreConstants.SPACE + 
					"AND g.serviceProviderName =:" + OAuth2CredentialEntity.serviceProviderNameProp
	),
	@NamedQuery(
			name = OAuth2CredentialEntity.QUERY_FIND_BY_IDEGA_USER_ID_AND_PROVIDER,
			query = "FROM OAuth2CredentialEntity ce " +
					"WHERE ce.idegaUser =:" + OAuth2CredentialEntity.idegaUserProp + 
					CoreConstants.SPACE + 
					"AND ce.serviceProviderName =:" + OAuth2CredentialEntity.serviceProviderNameProp
	)
})
public class OAuth2CredentialEntity implements Serializable {

	private static final long serialVersionUID = 5984835287070779833L;

	public static final String TABLE_NAME = "oauth2_credential";
	
	public static final String QUERY_FIND_ALL = "oAuth2CredentialEntity.findAll";
	public static final String QUERY_FIND_BY_ID = "oAuth2CredentialEntity.findByID";
	public static final String QUERY_FIND_BY_SERVICE_USER_ID = "oAuth2CredentialEntity.findByServiceUserId";
	public static final String QUERY_FIND_BY_SERVICE_USER_ID_AND_PROVIDER = "oAuth2CredentialEntity.findByServiceUserIdAndProvider"; 
	public static final String QUERY_FIND_BY_IDEGA_USER_ID_AND_PROVIDER = "oAuth2CredentialEntity.findByIdegaUserIdAndProvider";
	
	public static final String idProp = "id";
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    
    /** Access token or {@code null} for none. */
    public static final String accessTokenProp = "accessToken";
    public static final String COLUMN_ACCESS_TOKEN = "access_token";
    @Lob
    @Column(name = COLUMN_ACCESS_TOKEN, length = 5120)
    private String accessToken;

    /** Refresh token {@code null} for none. */
    public static final String refreshTokenProp = "refreshToken";
    public static final String COLUMN_REFRESH_TOKEN = "refresh_token";
    @Lob
    @Column(name = COLUMN_REFRESH_TOKEN, length = 5120)
    private String refreshToken;

    /** Expiration time in milliseconds {@code null} for none. */
    public static final String expirationTimeMillisProp = "expirationTimeMillis";
    public static final String COLUMN_EXPIRATION_TIME_IN_MILLIS = "expiration_time_in_millis";
    @Column(name = COLUMN_EXPIRATION_TIME_IN_MILLIS)
    private Long expirationTimeMillis;
    
    public static final String idegaUserProp = "idegaUser";
    public static final String COLUMN_IDEGA_USER = "idega_user_id";
    @Column(name = COLUMN_IDEGA_USER, nullable = false)
    private Long idegaUser;
    
    public static final String serviceUserIdProp = "serviceUserId";
    public static final String COLUMN_SERVICE_USER_ID = "service_user_id";
    @Column(name = COLUMN_SERVICE_USER_ID, nullable = false)
    private String serviceUserId;
    
    public static final String serviceProviderNameProp = "serviceProviderName";
    public static final String COLUMN_SERVICE_PROVIDER_NAME = "service_provider";
    @Column(name = COLUMN_SERVICE_PROVIDER_NAME, nullable = false)
    private String serviceProviderName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpirationTimeMillis() {
		return expirationTimeMillis;
	}

	public void setExpirationTimeMillis(Long expirationTimeMillis) {
		this.expirationTimeMillis = expirationTimeMillis;
	}

	public User getIdegaUser() {
		UserBusiness userBusiness = null;
		try {
			userBusiness = IBOLookup.getServiceInstance(
					CoreUtil.getIWContext(), 
					com.idega.user.business.UserBusiness.class);
		} catch (IBOLookupException e) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(
					Level.WARNING, "Failed to get " + UserBusiness.class + 
					" cause of: ", e);
		}
		if (userBusiness == null) {
			return null;
		}
		
		try {
			return userBusiness.getUser(idegaUser.intValue());
		} catch (RemoteException e) {
			java.util.logging.Logger.getLogger(getClass().getName()).log(
					Level.WARNING, "Unable to connect data source cause of: ", e);
		}
		
		return null;
	}

	public void setIdegaUser(User idegaUser) {
		if (idegaUser != null && idegaUser.getId() != null) {
			this.idegaUser = Long.valueOf(idegaUser.getId());
		}
	}

	public String getServiceUserId() {
		return serviceUserId;
	}

	public void setServiceUserId(String serviceUserId) {
		this.serviceUserId = serviceUserId;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}
}
