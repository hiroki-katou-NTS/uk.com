package nts.uk.ctx.at.shared.dom.scherec.workinfo;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * @author thanh_nx
 *
 *         実績と予定と労働条件データかで勤務情報と補正済み所定時間帯を取得する
 */
public class GetWorkInfoTimeZoneFromRcSc {

	//取得する
	public static Optional<WorkInfoAndTimeZone> getInfo(Require require, String cid, String employeeId,
			GeneralDate baseDate, Optional<WorkingConditionItem> workItem) {

		if (!workItem.isPresent()) {
			workItem = require.getWorkingConditionItem(employeeId, baseDate);
		}

		if (!workItem.isPresent())
			return Optional.empty();

		//  実績データから勤務情報を取得する
		Optional<WorkInformation> workInfoScheWork = require.getWorkInfoRc(employeeId, baseDate);
		Optional<WorkType> wTypeOpt = workInfoScheWork.isPresent() ? checkHolOrWorkHol(require, cid, workInfoScheWork.get())
				: Optional.empty();
		if (!wTypeOpt.isPresent()) {
			// 予定データから勤務情報を取得する
			workInfoScheWork = require.getWorkInfoSc(employeeId, baseDate);
			if (workInfoScheWork.isPresent()) {
				wTypeOpt = checkHolOrWorkHol(require, cid, workInfoScheWork.get());
			}
		}
		
		WorkInformation workInfo = null;
		if (wTypeOpt.isPresent()) {
			workInfo = workItem.get().getWorkCategory().getWorkinfoOnVacation(wTypeOpt.get());
		} else {
			workInfo = new WorkInformation(workItem.get().getWorkCategory().getWorkType().getHolidayWorkWTypeCode(),
					workItem.get().getWorkCategory().getWorkTime().getHolidayWork().getWorkTimeCode().orElse(null));

		}

		return workInfo.getWorkInfoAndTimeZone(require, cid);

	}

	// 休日または休日出勤かどうかチェックする
	private static Optional<WorkType> checkHolOrWorkHol(Require require, String cid, WorkInformation workInfo) {
		Optional<WorkType> wType = require.workType(cid, workInfo.getWorkTypeCode());
		if (!wType.isPresent())
			return Optional.empty();
		return (wType.get().isHoliday() || wType.get().isHolidayWork()) ? wType : Optional.empty();
	}

	public static interface Require extends WorkInformation.Require, WorkInformation.RequireM1 {

		// [R-1] 労働条件項目を取得
		public Optional<WorkingConditionItem> getWorkingConditionItem(String employeeId, GeneralDate baseDate);

		// [R-2] 予定データから勤務情報を取得する
		public Optional<WorkInformation> getWorkInfoSc(String employeeId, GeneralDate baseDate);

		//[R-3]  実績データから勤務情報を取得する
		public Optional<WorkInformation> getWorkInfoRc(String employeeId, GeneralDate baseDate);
	}

}
