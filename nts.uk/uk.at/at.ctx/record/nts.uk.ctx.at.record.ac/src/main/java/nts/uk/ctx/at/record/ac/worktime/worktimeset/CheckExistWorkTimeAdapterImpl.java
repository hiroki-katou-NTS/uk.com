package nts.uk.ctx.at.record.ac.worktime.worktimeset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.worktime.worktimeset.CheckExistWorkTimeAdapter;
import nts.uk.ctx.at.shared.pub.worktime.worktimeset.WorkTimeSettingPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckExistWorkTimeAdapterImpl implements CheckExistWorkTimeAdapter{

	@Inject
	private WorkTimeSettingPub workTimeSettingPub;

	@Override
	public boolean checkExistWorkTimeAdapter(String workTimeCD) {
		String companyID = AppContexts.user().companyId();
		return workTimeSettingPub.isExist(companyID, workTimeCD);
	}
	
	

}
