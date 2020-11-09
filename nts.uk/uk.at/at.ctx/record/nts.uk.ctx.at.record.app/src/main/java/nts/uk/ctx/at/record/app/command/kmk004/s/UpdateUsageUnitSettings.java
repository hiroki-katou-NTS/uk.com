package nts.uk.ctx.at.record.app.command.kmk004.s;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class UpdateUsageUnitSettings {

	@Inject
	private UsageUnitSettingRepository repo;
	
	public void updateUsageSetting (UpdateUsageUnitSettingsInput param) {
		String cid = AppContexts.user().companyId();
		
		UsageUnitSetting setting = new UsageUnitSetting(new CompanyId(cid), param.isEmployee(), param.isWorkPlace(), param.isEmployment());
		
		this.repo.update(setting);
		
	}
}
