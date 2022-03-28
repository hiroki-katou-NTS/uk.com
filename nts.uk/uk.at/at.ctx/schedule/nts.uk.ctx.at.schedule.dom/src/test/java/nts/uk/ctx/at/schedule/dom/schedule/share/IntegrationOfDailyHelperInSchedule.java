package nts.uk.ctx.at.schedule.dom.schedule.share;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class IntegrationOfDailyHelperInSchedule {
	
	private static WorkInfoOfDailyAttendance defaultWorkInfo = new WorkInfoOfDailyAttendance(
			new WorkInformation(new WorkTypeCode("001"), new WorkTimeCode("001")),
			CalculationState.No_Calculated,
			NotUseAttribute.Not_use,
			NotUseAttribute.Not_use,
			DayOfWeek.MONDAY,
			Collections.emptyList(),
			Optional.empty());
	
	/**
	 * 日別勤怠(Work)を作る
	 * @param ouenTime 応援時間
	 * @param ouenTimeSheet 応援時間帯
	 * @return
	 */
	public static IntegrationOfDaily createIntegrationOfDaily(
				String sid
			,	GeneralDate ymd
			,	AffiliationInforOfDailyAttd affiliationInfor
			,	List<OuenWorkTimeOfDailyAttendance> ouenTime
			,	List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheet ) {
		
		return new IntegrationOfDaily(
					sid
				,	ymd
				,	defaultWorkInfo
				,	CalAttrOfDailyAttd.createAllCalculate()
				,	affiliationInfor
				,	Optional.empty()
				,	Collections.emptyList()
				,	Optional.empty()
				,	new BreakTimeOfDailyAttd()
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	Optional.empty()
				,	Collections.emptyList()
				,	Optional.empty()
				,	Collections.emptyList()
				,	ouenTime
				,	ouenTimeSheet
				,	Optional.empty());
	}
	
}
