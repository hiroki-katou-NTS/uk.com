package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper.ErrorList;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.helper.MasterCache;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public class MasterAvailabilityPolicy {

	/**
	 * Check work type
	 * @param workType work type
	 * @param errList error list
	 * @return true if no error
	 */
	public static boolean checkWorkType(WorkType workType, ErrorList errList) {
		// ドメインモデル「勤務種類」に該当の勤務種類コードが存在するかチェック
		if (workType == null) {
			// set error to list
			errList.addMessage("Msg_436");
			return false;
		}

		if (workType.isDeprecated()) {
			// set error to list
			errList.addMessage("Msg_468");
			return false;
		}

		return true;
	}
	
	/**
	 * Check work time
	 * 
	 * @param workTimeSetting
	 * @param errList
	 * @return true if no error
	 */
	public static boolean checkWorkTime(WorkTimeSetting workTimeSetting, ErrorList errList) {
		// ドメインモデル「就業時間帯の設定」に該当の就業時間帯コードが存在するかチェック 
		if (workTimeSetting == null) {
			// Set error to list
			errList.addMessage("Msg_437");
			return false;
		}

		// ドメインモデル「就業時間帯の設定」.表示区分をチェック
		if (workTimeSetting.isAbolish()) {
			// Set error to list
			errList.addMessage("Msg_469");
			return false;
		}

		return true;
	}
	
	public static boolean checkParing(
			BasicScheduleService basicScheduleService,
			String workTypeCode,
			String workTimeCode,
			MasterCache masterCache,
			ErrorList errList) {
		WorkTimeSetting workTimeSetting = masterCache.getWorkTimeSetting(workTimeCode);
		
		// 勤務種類と就業時間帯のペアチェック (Kiểm tra cặp)
		try {
			if (workTimeSetting == null) {
				basicScheduleService.checkPairWTypeTimeWithLstWType(workTypeCode, workTimeCode, masterCache.getWorkTypes());
			} else {
				basicScheduleService.checkPairWTypeTimeWithLstWType(workTypeCode,
						workTimeSetting.getWorktimeCode().v(), masterCache.getWorkTypes());
			}
			return true;
		} catch (Exception ex) {
			if (ex.getCause() instanceof BusinessException) {
				BusinessException b = (BusinessException) ex.getCause();
				errList.addMessage(b);
			}
			return false;
		}
	}
}
