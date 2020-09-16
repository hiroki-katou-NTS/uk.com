package nts.uk.ctx.at.shared.dom.worktime.service;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * 流動勤務かどうかの判断処理
 * @author do_dt
 *
 */
public class WorkTimeIsFluidWork {
	
	/**
	 * 流動勤務かどうかの判断処理
	 * @param workTimeCode
	 * @return
	 */
	public static boolean checkWorkTimeIsFluidWork(RequireM2 require, String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		WorkTimeForm workTimeForm = WorkTimeForm.FIXED;
		//就業時間帯の設定
		Optional<WorkTimeSetting> findByCode = require.workTimeSetting(companyId, workTimeCode);
		if(!findByCode.isPresent()) {
			workTimeForm = WorkTimeForm.FIXED;
			return false;
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

	/**
	 * 所定時間を取得
	 * @param workTimeCode
	 * @param workTypeCode
	 * @return
	 */
	public static Integer getTimeByWorkTimeTypeCode(RequireM1 require, String workTimeCode, String workTypeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<PredetemineTimeSetting> pred = require.predetemineTimeSetting(companyId, workTimeCode);
		Optional<WorkType> workTypeInfor = require.workType(companyId, workTypeCode);
		if(pred.isPresent() 
				&& workTypeInfor.isPresent()) {
			PredetemineTimeSetting timeSetting = pred.get();
			WorkType workTypeData = workTypeInfor.get();
			if(workTypeData.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay) {
				return timeSetting.getPredTime().getPredTime().getOneDay().v();
			} else if (workTypeData.getDailyWork().decisionNeedPredTime() == AttendanceHolidayAttr.MORNING) {
				return timeSetting.getPredTime().getPredTime().getMorning().v();
			} else if (workTypeData.getDailyWork().decisionNeedPredTime() == AttendanceHolidayAttr.AFTERNOON) {
				return timeSetting.getPredTime().getPredTime().getAfternoon().v();
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	public static interface RequireM2 {

		Optional<WorkTimeSetting> workTimeSetting(String companyId, String code);

	}
	
	public static interface RequireM1 {

		Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, String workTimeCode);

		Optional<WorkType> workType(String companyId, String workTypeCd);

	}
}
