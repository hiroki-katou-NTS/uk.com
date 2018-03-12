package nts.uk.ctx.pereg.ac.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.worktime.predset.PredetemineTimeSettingPub;
import nts.uk.ctx.pereg.dom.common.PredetemineTimeSettingRepo;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PredetemineTimeSettingAC implements PredetemineTimeSettingRepo{

	@Inject
	private PredetemineTimeSettingPub predetemineTimeSetting;
	
	@Override
	public boolean isWorkingTwice(String workTimeCode) {
		return predetemineTimeSetting.IsWorkingTwice(AppContexts.user().companyId(), workTimeCode);
	}
	
}
