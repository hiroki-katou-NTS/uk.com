package nts.uk.ctx.at.shared.dom.worktime.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTimeIsFluidWorkImpl implements WorkTimeIsFluidWork{
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Override
	public boolean checkWorkTimeIsFluidWork(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		WorkTimeForm workTimeForm = WorkTimeForm.FIXED;
		//就業時間帯の設定
		Optional<WorkTimeSetting> findByCode = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		if(!findByCode.isPresent()) {
			workTimeForm = WorkTimeForm.FIXED;
		}
		WorkTimeSetting workTimeData = findByCode.get();
		if(workTimeData.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.FLEX_WORK) {
			workTimeForm = WorkTimeForm.FLEX;
		} else if (workTimeData.getWorkTimeDivision().getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK) {
			if(workTimeData.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {
				workTimeForm = WorkTimeForm.FIXED;
			} else if (workTimeData.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.DIFFTIME_WORK) {
				workTimeForm = WorkTimeForm.TIMEDIFFERENCE;
			} else {
				workTimeForm = WorkTimeForm.FLOW;
			}
		}
		if(workTimeForm == WorkTimeForm.FLOW) {
			return true;
		} else {
			return false;
		}
	}

}
