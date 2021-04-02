package nts.uk.screen.at.ws.kdp.kdp002.b;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.query.kdp.kdp002.a.SettingsStampCommon;
import nts.uk.screen.at.app.query.kdp.kdp002.a.SettingsStampCommonDto;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStamp;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStampDto;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStampInput;

/**
 * 
 * @author chungnt
 *
 */
@Path("screen/at/kdp002/b/")
@Produces("application/json")
public class Kdp002BWebService {

	@Inject
	private ContentOfNotificationByStamp notification;
	
	@Inject
	private SettingsStampCommon settingsStampCommon;
	
	@POST 
	@Path("notification_by_stamp")
	public ContentOfNotificationByStampDto notificationByStamp(ContentOfNotificationByStampInput param){
		return notification.get(param);
	}
	
	@POST 
	@Path("settingsStampCommon")
	public SettingsStampCommonDto settingsStampCommon(){
		return settingsStampCommon.getSettingCommonStamp();
	}
	
}
