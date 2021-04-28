package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInput;
import nts.uk.ctx.at.record.dom.stamp.application.CommonSettingsStampInputRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetSettingNoti {

	@Inject
	private CommonSettingsStampInputRepository repo;
	
	public boolean getSetting() {
		
		String cid = AppContexts.user().companyId();
		
		Optional<CommonSettingsStampInput> setting = repo.get(cid);
		
		if (!setting.isPresent()) {
			return false;
		}
		
		return setting.get().getSupportUseArt().value == 1 ? true : false;
	}
	
}
