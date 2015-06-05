/**
 * @(#)YoutubeServiceImpl.java    1.0.0 19:47:57
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
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.idega.block.oauth2.client.business.YoutubeService;
import com.idega.core.business.DefaultSpringBean;
import com.idega.util.ListUtil;
import com.idega.util.expression.ELUtil;

/**
 * <p>Provides stuff for youtube service</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 2015 birž. 4
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
/*
 * Spring
 */
@Service(YoutubeServiceImpl.BEAN_NAME)
@Scope(BeanDefinition.SCOPE_SINGLETON)
//@EnableAspectJAutoProxy(proxyTargetClass=true)

/*
 * DWR
 */
@RemoteProxy(
	name=YoutubeServiceImpl.JAVASCRIPT_CLASS_NAME,
	creator=SpringCreator.class,
	creatorParams={
	@Param(
		name="beanName",
		value=YoutubeServiceImpl.BEAN_NAME),
	@Param(
		name="javascript",
		value=YoutubeServiceImpl.JAVASCRIPT_CLASS_NAME)
	}
)
public class YoutubeServiceImpl extends DefaultSpringBean implements
		YoutubeService {


	public static final String BEAN_NAME = "youtubeService";

	public static final String JAVASCRIPT_CLASS_NAME = "YoutubeService";

	@Autowired
	private GoogleAuthorizationService googleAuthorizationService;

	protected GoogleAuthorizationService getGoogleAuthorizationService() {
		if (this.googleAuthorizationService == null) {
			ELUtil.getInstance().autowire(this);
		}

		return this.googleAuthorizationService;
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.YoutubeService#getService(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public YouTube getService(
			String applicationName, 
			Credential credentials) {
		return new YouTube.Builder(
				new NetHttpTransport(), 
				new JacksonFactory(),
				credentials).setApplicationName(applicationName).build();
	}

	/*
	 * (non-Javadoc)
	 * @see com.idega.block.oauth2.client.business.YoutubeService#getChannels(java.lang.String, com.google.api.client.auth.oauth2.Credential)
	 */
	@Override
	public List<Channel> getChannels(
			String applicationName, 
			Credential credentials) {
		YouTube service = getService(applicationName, credentials);
		if (service != null) {
			// Call the API's channels.list method to retrieve the
            // resource that represents the authenticated user's channel.
            // In the API response, only include channel information needed for
            // this use case. The channel's contentDetails part contains
            // playlist IDs relevant to the channel, including the ID for the
            // list that contains videos uploaded to the channel.
			YouTube.Channels.List channelRequest = null;
			try {
				channelRequest = service.channels().list("contentDetails");
			} catch (IOException e) {
				getLogger().log(Level.WARNING, 
						"Failed to get channels list, cause of:", e);
			}

			channelRequest.setMine(true);
	        channelRequest.setFields("items/contentDetails,nextPageToken,pageInfo");

	        ChannelListResponse channelResult = null;
			try {
				channelResult = channelRequest.execute();
			} catch (IOException e) {
				getLogger().log(Level.WARNING, 
						"Failed to get channel list cause of: ", e);
			}

	        if (channelResult != null) {
	        	return channelResult.getItems();
	        }
		}

		return Collections.emptyList();
	}

	@Override
	public List<PlaylistItem> getUploads(
			String applicationName, 
			Credential credentials) {
		// Define a list to store items in the list of uploaded videos.
        List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

		List<Channel> personalChannels = getChannels(applicationName, credentials);
		if (!ListUtil.isEmpty(personalChannels)) {
			// The user's default channel is the first item in the list.
	        // Extract the playlist ID for the channel's videos from the
	        // API response.
	        String uploadPlaylistId = personalChannels.get(0).getContentDetails()
	        		.getRelatedPlaylists().getUploads();

	        // Retrieve the playlist of the channel's uploaded videos.
	        YouTube.PlaylistItems.List playlistRequest = null;
			try {
				playlistRequest = getService(applicationName, credentials).playlistItems().list("id,contentDetails,snippet");
			} catch (IOException e) {
				getLogger().log(Level.WARNING, "Failed to get playlist", e);
			}

	        playlistRequest.setPlaylistId(getApplicationProperty("youtube.playlist.ids", "PLbOPjwR6aLj-te5aF-dI_ZCJ1mgPvqF3N"));

	        // Only retrieve data used in this application, thereby making
	        // the application more efficient. See:
	        // https://developers.google.com/youtube/v3/getting-started#partial
	        playlistRequest.setFields(
	                "items(contentDetails/videoId,snippet),nextPageToken,pageInfo");

	        String nextToken = "";

	        
	        
	        // Call the API one or more times to retrieve all items in the
	        // list. As long as the API response returns a nextPageToken,
	        // there are still more items to retrieve.
	        do {
	            playlistRequest.setPageToken(nextToken);
	            PlaylistItemListResponse playlistResponse = null;
				try {
					playlistResponse = playlistRequest.execute();
				} catch (IOException e) {
					getLogger().log(Level.WARNING, "Failed to get playlist", e);
				}

	            playlistItemList.addAll(playlistResponse.getItems());

	            nextToken = playlistResponse.getNextPageToken();
	        } while (nextToken != null);
		}

		return playlistItemList;
	}
}
