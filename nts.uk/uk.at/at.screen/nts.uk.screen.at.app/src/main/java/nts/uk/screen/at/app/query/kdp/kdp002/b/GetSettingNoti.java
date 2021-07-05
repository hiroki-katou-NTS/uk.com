package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetSettingNoti {

	@Inject
	private NoticeSetRepository repo;
	
	public boolean getSetting() {
		
		String cid = AppContexts.user().companyId();
		
		Optional<NoticeSet> setting = repo.get(cid);
		
		if (!setting.isPresent()) {
			return false;
		}
		
		return setting.get().getDisplayAtr().value == 1 ? true : false;
	}
	
}
