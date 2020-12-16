package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetUsingShiftTableRuleOfEmployeeService;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * 勤務希望を提出する
 * @author lan_lt
 *
 */
public class RegisterWorkAvailability {
	
	/**
	 * 提出する
	 * @param require
	 * @param workOneDay 一日分の勤務希望
	 * @param datePeriod 期間
	 * @return
	 */
	public static AtomTask register(Require require, List<WorkAvailabilityOfOneDay> workOneDays, DatePeriod datePeriod) {
		val workOneDay = workOneDays.get(0);
		val shiftRuleOpt = GetUsingShiftTableRuleOfEmployeeService.get(require, workOneDay.getEmployeeId(), datePeriod.end());
		if (!shiftRuleOpt.isPresent()) {
			throw new BusinessException("Msg_2049");
		}

		val shiftRule = shiftRuleOpt.get();
		if (shiftRule.getUseWorkAvailabilityAtr() == NotUseAtr.NOT_USE) {
			throw new BusinessException("Msg_2052");
		}

		// 勤務希望運用区分 == する場合は、必ず「シフト表の設定」emptyではないため。
		val shiftTableRule = shiftRule.getShiftTableSetting().get();
		if (shiftTableRule.isOverDeadline(workOneDay.getWorkAvailabilityDate())) {
			throw new BusinessException("Msg_2050");
		}

		if (shiftTableRule.isOverHolidayMaxDays(workOneDays)) {
			throw new BusinessException("Msg_2051");
		}
		
		return AtomTask.of(() -> {
			require.deleteAllWorkAvailabilityOfOneDay(workOneDay.getEmployeeId(), datePeriod);
			require.insertAllWorkAvailabilityOfOneDay(workOneDays);
		});
		
	}
	
	public static interface Require extends GetUsingShiftTableRuleOfEmployeeService.Require{
		
		/**
		 * [R-1] 一日分の勤務希望を追加する
		 * @param workOneDay　一日分の勤務希望リスト
		 */
		void insertAllWorkAvailabilityOfOneDay(List<WorkAvailabilityOfOneDay> workOneDays);
		
		/**
		 * [R-2] 一日分の勤務希望を削除する
		 * @param sid 社員ID
		 * @param datePeriod 期間
		 */
		void deleteAllWorkAvailabilityOfOneDay(String sid, DatePeriod datePeriod);
		
	}
}
