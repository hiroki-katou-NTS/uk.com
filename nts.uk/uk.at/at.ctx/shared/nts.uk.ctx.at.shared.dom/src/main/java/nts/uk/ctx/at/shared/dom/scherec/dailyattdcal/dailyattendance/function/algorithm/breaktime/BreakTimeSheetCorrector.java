package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

public class BreakTimeSheetCorrector {

	public static void correct(RequireM1 require, IntegrationOfDaily dailyRecord, boolean fixedBreakCorrect) {

		val cid = AppContexts.user().companyId();
		
		val workType = require.workType(cid, dailyRecord.getWorkInformation().getRecordInfo().getWorkTypeCode().v()).orElse(null);
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
		
		/** [変更状態.勤務情報]をチェック */
		if (isFixedBreak == BreakTimeType.FIXED_BREAK && !fixedBreakCorrect) {
			
			return;
		}
		
		/** 休憩時間帯を補正する */
		correctBreakTime(require, dailyRecord, 
				require.managePerPersonDailySet(dailyRecord.getEmployeeId(), dailyRecord.getYmd(), dailyRecord));
	}

	/** 休憩時間帯を補正する */
	private static void correctBreakTime(RequireM1 require, IntegrationOfDaily dailyRecord,
			Optional<ManagePerPersonDailySet> personDailySetting) {
		
		if(!personDailySetting.isPresent()) {
			return;
		}
		
		/** 休憩時間帯取得 */
		val breakTime = BreakTimeSheetGetter.get(require, personDailySetting.get(), dailyRecord, false);
		
		/** 休憩時間帯をマージする */
		val converter = require.createDailyConverter();
		converter.setData(dailyRecord);
		val edittedItems = dailyRecord.getEditState().stream().map(c -> c.getAttendanceItemId()).collect(Collectors.toList());
		if(edittedItems.isEmpty()) {
			dailyRecord.setBreakTime(new BreakTimeOfDailyAttd(breakTime));
			return;
		}
		val oldValues = converter.convert(edittedItems);
		converter.withBreakTime(dailyRecord.getEmployeeId(), dailyRecord.getYmd(), new BreakTimeOfDailyAttd(breakTime));
		converter.merge(oldValues);
		val merged = converter.breakTime();
		dailyRecord.setBreakTime(merged);
	}
	
	/** 勤務情報が固定休憩かの確認 */
	private static BreakTimeType isFixedBreakTime(RequireM1 require, String cid,
			WorkType workType, IntegrationOfDaily dailyRecord) {
		
		/** 就業時間帯コード */
		val wtc = dailyRecord.getWorkInformation().getRecordInfo().getWorkTimeCodeNotNull().map(c -> c.v()).orElse(null);
		if (wtc == null) {
			
			return BreakTimeType.CANT_CHECK;
		}
		val workTimeSet = require.workTimeSetting(cid, wtc).orElse(null);
		
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
		
		Optional<ManagePerPersonDailySet> managePerPersonDailySet(String sid, GeneralDate ymd, IntegrationOfDaily dailyRecord);
		
		DailyRecordToAttendanceItemConverter createDailyConverter();
		
		Optional<WorkType> workType(String companyId, String workTypeCd);
		
		Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode);
		
		Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode);
		
		Optional<FlexWorkSetting> flexWorkSetting(String companyId,String workTimeCode);
	}
}
