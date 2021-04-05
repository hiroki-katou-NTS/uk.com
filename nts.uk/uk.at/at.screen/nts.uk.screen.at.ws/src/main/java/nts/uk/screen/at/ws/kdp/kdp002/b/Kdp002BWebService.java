package nts.uk.screen.at.ws.kdp.kdp002.b;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.query.kdp.kdp002.a.GetWorkManagementMultiple;
import nts.uk.screen.at.app.query.kdp.kdp002.a.SettingsStampCommon;
import nts.uk.screen.at.app.query.kdp.kdp002.a.SettingsStampCommonDto;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStamp;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStampDto;
import nts.uk.screen.at.app.query.kdp.kdp002.b.ContentOfNotificationByStampInput;
import nts.uk.screen.at.app.query.kdp.kdp002.b.SettingEmojiByStamp;

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
	
	@Inject
	private GetWorkManagementMultiple getWorkManagementMultiple;
	
	@Inject
	private SettingEmojiByStamp settingEmojiByStamp;
	
	@POST 
	@Path("notification_by_stamp")
	public ContentOfNotificationByStampDto notificationByStamp(ContentOfNotificationByStampInput param){
		return notification.get(param);
	}
	
	@POST 
	@Path("settings_stamp_common")
	public SettingsStampCommonDto settingsStampCommon(){
		return settingsStampCommon.getSettingCommonStamp();
	}
	
	@POST 
	@Path("work_management_multiple")
	public boolean getWorkManagementMultiple(){
		return getWorkManagementMultiple.getWorkManagementMultiple();
	}
	
	// 打刻入力でニコニコマークの表示設定を取得する
	@POST 
	@Path("setting_emoji_stamp")
	public boolean get(){
		return settingEmojiByStamp.getSettingEmoji();
	}
	
}
