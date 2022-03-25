package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/** 欠勤使用時間を計算 */
public class AbsenceUseTimeCalc {

	/** 計算する */
	public static AttendanceTime calc(Require require, String cid, String sid, GeneralDate baseDate, WorkInformation workInfo) {
		
		/** $勤務種類 = require.勤務種類を取得する(会社ID, 勤務情報.勤務種類コード) */
		val workType = require.workType(cid, workInfo.getWorkTypeCode().v()).orElse(null);
		if (workType == null) {
			return AttendanceTime.ZERO;
		}
		
		/** $欠勤の1日午前午後区分 = $勤務種類.欠勤の1日午前午後区分を取得() */
		val workAtr = workType.getWorkAtrForAbsenceDay().orElse(null);
		
		/** if $欠勤の１日午前午後区分 not isPresent() */
		if (workAtr == null) 
			/** return 勤怠時間(0:00) */
			return AttendanceTime.ZERO;
		
		/** $休暇加算時間設定 = require.休暇加算時間設定を取得する(会社ID) */
		val holAddSet = require.holidayAddtionSet(cid).orElse(null);
		if (holAddSet == null) {
			return AttendanceTime.ZERO;
		}
		
		/** $1日の内訳 = $休暇加算時間設定.参照先.休暇加算時間の取得 */
		val brdTimeDay = holAddSet.getReference().getVacationAddTime(require, cid, sid, workInfo, baseDate);
		
		/** $１日の内訳.取得($欠勤の１日午前午後区分) */
		return brdTimeDay.get(workAtr);
	}
	
	public static interface Require extends RefDesForAdditionalTakeLeave.Require {
		
		Optional<WorkType> workType(String cid, String workTypeCode);
		
		Optional<HolidayAddtionSet> holidayAddtionSet(String cid);
	}
}
