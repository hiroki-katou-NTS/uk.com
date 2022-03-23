package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.premiumtarget.getvacationaddtime.AddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/** 就業時間の加算時間を取得する */
public class WorkTimeAddtionTimeGetter {

	/** 割増時間の加算時間を計算する */
	public static AttendanceTimeMonth getForPremiumTime(Require require,
			List<IntegrationOfDaily> dailies, WorkingSystem workingSystem,
			String cid, MonthlyAggregateAtr aggregateAtr, Optional<AddSet> addSet) {
		
		/**　集計区分で加算時間を取得するか */
		if (!canGetForAggregateAtr(require, cid, aggregateAtr)) 
			return new AttendanceTimeMonth(0);
		
		/** 休暇加算時間設定を取得する */
		val holidayAddtionSet = require.holidayAddtionSet(cid);
		
		/** 休暇加算時間設定に加算設定を上書きする */
		val holAddSet = holidayAddtionSet.flatMap(has -> addSet.map(as -> new HolidayAddtionSet(cid, 
												has.getReference(), 
												new LeaveSetAdded(as.isAnnualLeave() ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
																	as.isRetentionYearly() ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
																	as.isSpecialHoliday() ? NotUseAtr.USE : NotUseAtr.NOT_USE), 
												has.getTimeHolidayAddition())));
		
		/** 労働時間の加算設定を取得する */
		val laborTimeAddSet = getAddSet(require, cid, workingSystem);
		
		/** 集計用の加算設定を作成する */
		val addSetMonth = WorkTimeAddTimeSetForMonthAggr.forPremium(laborTimeAddSet);

		/** 加算時間を取得する */
		return addSetMonth.calcAddTime(require, dailies, holAddSet);
	}

	/** 就業時間の加算時間を計算する */
	public static AttendanceTimeMonth getForWorkTime(Require require,
			List<IntegrationOfDaily> dailies, WorkingSystem workingSystem,
			String cid, MonthlyAggregateAtr aggregateAtr, Optional<AddSet> addSet) {
		
		/**　集計区分で加算時間を取得するか */
		if (!canGetForAggregateAtr(require, cid, aggregateAtr)) 
			return new AttendanceTimeMonth(0);
		
		/** 休暇加算時間設定を取得する */
		val holidayAddtionSet = require.holidayAddtionSet(cid);
		
		/** 休暇加算時間設定に加算設定を上書きする */
		val holAddSet = addSet.flatMap(as -> holidayAddtionSet.map(has -> new HolidayAddtionSet(cid, 
				has.getReference(), 
				new LeaveSetAdded(as.isAnnualLeave() ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
									as.isRetentionYearly() ? NotUseAtr.USE : NotUseAtr.NOT_USE, 
									as.isSpecialHoliday() ? NotUseAtr.USE : NotUseAtr.NOT_USE), 
				has.getTimeHolidayAddition())));
		
		/** 労働時間の加算設定を取得する */
		val laborTimeAddSet = getAddSet(require, cid, workingSystem);
		
		/** 集計用の加算設定を作成する */
		val addSetMonth = WorkTimeAddTimeSetForMonthAggr.forWorkTime(laborTimeAddSet);
		
		/** 加算時間を取得する */
		return addSetMonth.calcAddTime(require, dailies, holAddSet);
	}
	
	/** 労働時間の加算設定を取得する */
	private static AddSettingOfWorkingTime getAddSet(RequireM2 require, 
			String cid, WorkingSystem workingSystem) {
		
		switch (workingSystem) {
		case FLEX_TIME_WORK:
			
			return require.workFlexAdditionSet(cid)
					.map(c -> c.getAddSetOfWorkingTime())
					.orElseThrow(() -> new RuntimeException("労働時間の加算設定が存在しない"));
		case VARIABLE_WORKING_TIME_WORK:
			
			return require.workDeformedLaborAdditionSet(cid)
					.map(c -> c.getAddSetOfWorkingTime())
					.orElseThrow(() -> new RuntimeException("労働時間の加算設定が存在しない"));
		case REGULAR_WORK:
		default:
			return require.workRegularAdditionSet(cid)
					.map(c -> c.getAddSetOfWorkingTime())
					.orElseThrow(() -> new RuntimeException("労働時間の加算設定が存在しない"));
		}
	}
	
	/** 集計区分で加算時間を取得するか */
	private static boolean canGetForAggregateAtr(RequireM1 require, String cid, 
			MonthlyAggregateAtr aggregateAtr) {
		
		/** 集計区分を確認する */
		if (aggregateAtr != MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK) return true;
		
		/** 就業時間の加算設定管理を取得する */
		return require.addSetManageWorkHour(cid)
				.map(c -> c.getAdditionSettingOfOvertime() == NotUseAtr.USE)
				.orElse(false);
	}

	public static interface RequireM2 {
		
		Optional<WorkFlexAdditionSet> workFlexAdditionSet(String cid);
		
		Optional<WorkRegularAdditionSet> workRegularAdditionSet(String cid);
		
		Optional<WorkDeformedLaborAdditionSet> workDeformedLaborAdditionSet(String cid);
	}
	
	public static interface RequireM1 {
		
		Optional<AddSetManageWorkHour> addSetManageWorkHour(String cid);
	}
	
	public static interface Require extends RequireM1, RequireM2, WorkTimeAddTimeSetForMonthAggr.Require {
		
		Optional<HolidayAddtionSet> holidayAddtionSet(String cid);
	}
}
