/**
 * @(#)GoogleCredentialEntityDAOImpl.java    1.0.0 11:24:01 AM
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
package com.idega.block.oauth2.client.data.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.auth.oauth2.Credential;
import com.idega.block.oauth2.client.OAuth2LoginConstants;
import com.idega.block.oauth2.client.data.OAuth2CredentialEntity;
import com.idega.block.oauth2.client.data.dao.OAuth2CredentialEntityDAO;
import com.idega.core.persistence.Param;
import com.idega.core.persistence.impl.GenericDaoImpl;
import com.idega.user.data.User;
import com.idega.util.StringUtil;

/**
 * @see OAuth2CredentialEntityDAO
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Jun 25, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
@Repository(OAuth2CredentialEntityDAO.BEAN_NAME)
@Transactional(readOnly = false)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class OAuth2CredentialEntityDAOImpl extends GenericDaoImpl implements
		OAuth2CredentialEntityDAO {
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#findAll()
	 */
	@Override
	public List<OAuth2CredentialEntity> findAll() {
		return getResultList(
				OAuth2CredentialEntity.QUERY_FIND_ALL, 
				OAuth2CredentialEntity.class);
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#findById(java.lang.Long)
	 */
	@Override
	public OAuth2CredentialEntity findById(Long id) {
		return getSingleResult(OAuth2CredentialEntity.QUERY_FIND_BY_ID, 
				OAuth2CredentialEntity.class, 
				new Param(OAuth2CredentialEntity.idProp, id));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#findByGoogleUserId(java.lang.String)
	 */
	@Override
	public List<OAuth2CredentialEntity> findByServiceUserId(String id) {
		return getResultList(OAuth2CredentialEntity.QUERY_FIND_BY_SERVICE_USER_ID, 
				OAuth2CredentialEntity.class, 
				new Param(OAuth2CredentialEntity.serviceUserIdProp, id));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.data.dao.OAuth2CredentialEntityDAO#findByServiceUserIdAndProvider(java.lang.String, java.lang.String)
	 */
	@Override
	public OAuth2CredentialEntity findByServiceUserIdAndProvider(String id, String provider) {
		return getSingleResult(
				OAuth2CredentialEntity.QUERY_FIND_BY_SERVICE_USER_ID_AND_PROVIDER, 
				OAuth2CredentialEntity.class, 
				new Param(OAuth2CredentialEntity.serviceUserIdProp, id), 
				new Param(OAuth2CredentialEntity.serviceProviderNameProp, provider));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#update(com.idega.block.calendar.data.GoogleCredentialEntity)
	 */
	@Override
	public OAuth2CredentialEntity update(OAuth2CredentialEntity entity) {
		if (entity == null) {
			return null;
		}
		
		String message = null;
		if (entity.getId() == null) {
			persist(entity);
			message = OAuth2CredentialEntity.class + " created!";
		} else {
			merge(entity);
			message = OAuth2CredentialEntity.class + " updated!";
		}
		
		if (entity.getId() == null) {
			message = "Failed to update/persist " + OAuth2CredentialEntity.class;
		}
		
		getLogger().info(message);
		return entity.getId() == null ? null : entity;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#update(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, com.idega.user.data.User, java.lang.String, java.lang.String)
	 */
	@Override
	public OAuth2CredentialEntity update(
			Long id, 
			Long expirationTimeMillis,
			String refreshToken,
			String accessToken,
			User user,
			String serviceUserId, 
			String serviceProviderName) {
		
		OAuth2CredentialEntity gce = null;
		if (id != null) {
			gce = findById(id);
		} else if (!StringUtil.isEmpty(serviceUserId) && !StringUtil.isEmpty(serviceProviderName)) {
			gce = findByServiceUserIdAndProvider(serviceUserId, serviceProviderName);
		} 
		
		if (gce == null) {
			gce = new OAuth2CredentialEntity();
		}
		
		if (user != null) {
			gce.setIdegaUser(user);
		}
		
		if (expirationTimeMillis != null) {
			gce.setExpirationTimeMillis(expirationTimeMillis);
		}
		
		if (!StringUtil.isEmpty(refreshToken)) {
			gce.setRefreshToken(refreshToken);
		}
		
		if (!StringUtil.isEmpty(accessToken)) {
			gce.setAccessToken(accessToken);
		}
		
		if (!StringUtil.isEmpty(serviceUserId)) {
			gce.setServiceUserId(serviceUserId);
		}
		
		if (!StringUtil.isEmpty(serviceProviderName)) {
			gce.setServiceProviderName(serviceProviderName);
		}
		
		return update(gce);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.idega.block.calendar.data.dao.GoogleCredentialEntityDAO#remove(com.idega.block.calendar.data.GoogleCredentialEntity)
	 */
	@Override
	public void remove(OAuth2CredentialEntity obj) {
		super.remove(obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.api.client.auth.oauth2.CredentialStore#load(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public boolean load(String userId, Credential credential)
			throws IOException {
		if (StringUtil.isEmpty(userId) || credential == null) {
			return Boolean.FALSE;
		}
		
		OAuth2CredentialEntity entity = findByServiceUserIdAndProvider(
				userId, OAuth2LoginConstants.GOOGLE);
		if (entity == null) {
			return Boolean.FALSE;
		}
		
		credential.setAccessToken(entity.getAccessToken());
		credential.setRefreshToken(entity.getRefreshToken());
		credential.setExpirationTimeMilliseconds(entity.getExpirationTimeMillis());
		
		return Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.api.client.auth.oauth2.CredentialStore#store(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public void store(String userId, Credential credential) throws IOException {
		if (StringUtil.isEmpty(userId)) {
			return;
		}
		
		// If we need to update tokens only
		OAuth2CredentialEntity entity = findByServiceUserIdAndProvider(
				userId, OAuth2LoginConstants.GOOGLE);
		if (entity != null) {
			update(entity.getId(), credential.getExpirationTimeMilliseconds(),
					credential.getRefreshToken(), credential.getAccessToken(), 
					null, null, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.api.client.auth.oauth2.CredentialStore#delete(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public void delete(String userId, Credential credential) throws IOException {
		OAuth2CredentialEntity entity = findByServiceUserIdAndProvider(
				userId, OAuth2LoginConstants.GOOGLE);
		if (entity != null) {
			remove(entity);
		}
	}
}
