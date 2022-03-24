package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public class BreakTimeSheetCorrector {

	public final static List<Integer> BREAKTIME_ID = Arrays.asList(157, 159, 163, 165, 171, 169, 177, 175, 183, 181,
			189, 187, 195, 193, 199, 201, 205, 207, 211, 213);
			
	public static void correct(RequireM1 require, String cid, IntegrationOfDaily dailyRecord, boolean correctValCopyFromSche) {

<<<<<<< HEAD
		val workType = require.workType(cid, dailyRecord.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).orElse(null);
=======
		val cid = AppContexts.user().companyId();
		
		val workType = require.workType(cid, dailyRecord.getWorkInformation().getRecordInfo().getWorkTypeCode()).orElse(null);
>>>>>>> pj/at/release_ver4
		if (workType == null) {
			return;
		}
		/** 出勤系か判定する */
		if (workType.getAttendanceHolidayAttr() == AttendanceHolidayAttr.HOLIDAY) {
			
			/** 休憩をクレアする */
			dailyRecord.setBreakTime(new BreakTimeOfDailyAttd());
			return;
		}
		
		/** 勤務情報が固定休憩かの確認 */
		val isFixedBreak = isFixedBreakTime(require, cid, workType, dailyRecord);
		if (isFixedBreak == BreakTimeType.CANT_CHECK) {
			
			return;
		}
		
		/** 勤務予定から移送した値も補正するをチェック */
		if (isFixedBreak == BreakTimeType.FIXED_BREAK && !correctValCopyFromSche) {
			
			return;
		}
		
		/** 休憩時間帯を補正する */
		correctBreakTime(require, dailyRecord, 
				require.managePerCompanySet(),
				require.managePerPersonDailySet(dailyRecord.getEmployeeId(), dailyRecord.getYmd(), dailyRecord));
	}

	/** 休憩時間帯を補正する */
	private static void correctBreakTime(RequireM1 require, IntegrationOfDaily dailyRecord,
			ManagePerCompanySet companyCommonSetting,
			Optional<ManagePerPersonDailySet> personDailySetting) {
		
		if(!personDailySetting.isPresent()) {
			return;
		}
		
		val edittedItems = dailyRecord.getEditState().stream().map(c -> c.getAttendanceItemId())
				.filter(x -> BREAKTIME_ID.contains(x))
				.collect(Collectors.toList());
		if (!edittedItems.isEmpty())
			return;
		/** 休憩時間帯取得 */
		val breakTime = BreakTimeSheetGetter.get(require,
				companyCommonSetting, personDailySetting.get(), dailyRecord, false);
		
		/** 休憩時間帯をマージする */
		dailyRecord.setBreakTime(new BreakTimeOfDailyAttd(breakTime));
		return;
	}
	
	/** 勤務情報が固定休憩かの確認 */
	private static BreakTimeType isFixedBreakTime(RequireM1 require, String cid,
			WorkType workType, IntegrationOfDaily dailyRecord) {
		
		/** 就業時間帯コード */
		val wtc = dailyRecord.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull().map(c -> c).orElse(null);
		if (wtc == null) {
			
			return BreakTimeType.CANT_CHECK;
		}
		
		val workTimeSet = require.workTimeSetting(cid, wtc).orElse(null);
		if (workTimeSet == null) {
			
			return BreakTimeType.CANT_CHECK;
		}
		/**　勤務情報の就業時間帯が固定勤務かをチェックする　*/
		if(workTimeSet.getWorkTimeDivision().getWorkTimeMethodSet() == WorkTimeMethodSet.FIXED_WORK) {

			return BreakTimeType.FIXED_BREAK;
		}
		
		/** 勤務情報の勤務種類の[勤務種類の分類]が休日出勤かをチェック */
		FlowWorkRestTimezone restTimeZone;
		switch (workTimeSet.getWorkTimeDivision().getWorkTimeForm()) {
		case FLEX:
			restTimeZone = require.flexWorkSetting(cid, wtc).map(c -> c.getFlowWorkRestTimezone(workType)).orElse(null);
			break;
		case FLOW:
			restTimeZone = require.flowWorkSetting(cid, wtc).map(c -> c.getFlowWorkRestTimezone(workType)).orElse(null);
			break;
		default:
			return BreakTimeType.CANT_CHECK;
		}
		
		if (restTimeZone == null) {
			return BreakTimeType.CANT_CHECK;
		}
		
		return restTimeZone.isFixRestTime() ? BreakTimeType.FIXED_BREAK : BreakTimeType.FLOW_BREAK;
	}
	
	/** 休憩種類 */
	private static enum BreakTimeType {
		
		FIXED_BREAK, /** 固定休憩 */
		FLOW_BREAK, /** 流動休憩 */
		CANT_CHECK; /** 確認できない */
	}
	
	public static interface RequireM1 extends BreakTimeSheetGetter.RequireM1 {

		ManagePerCompanySet managePerCompanySet();
		
		Optional<ManagePerPersonDailySet> managePerPersonDailySet(String sid, GeneralDate ymd, IntegrationOfDaily dailyRecord);
		
<<<<<<< HEAD
		DailyRecordToAttendanceItemConverter createDailyConverter(String cid);
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
		
		Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode);
		
		Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode);
		
		Optional<FlexWorkSetting> flexWorkSetting(String companyId,String workTimeCode);
=======
		DailyRecordToAttendanceItemConverter createDailyConverter();
>>>>>>> pj/at/release_ver4
	}
}
