package nts.uk.ctx.pereg.ac.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.pub.worktime.worktimeset.WorkTimeSettingPub;
import nts.uk.ctx.pereg.dom.common.WorkTimeSettingRepo;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class WorkTimeSettingAC implements WorkTimeSettingRepo{
	
	@Inject
	private WorkTimeSettingPub workTimeSetting;

	@Override
	public boolean isFlowWork(String workTimeCode) {
		return workTimeSetting.isFlowWork(AppContexts.user().companyId(), workTimeCode);
	}

	@Override
	public String getWorkTimeSettingName(String companyId, String worktimeCode) {
		return workTimeSetting.getWorkTimeSettingName(companyId, worktimeCode);
	}
	
	
}
