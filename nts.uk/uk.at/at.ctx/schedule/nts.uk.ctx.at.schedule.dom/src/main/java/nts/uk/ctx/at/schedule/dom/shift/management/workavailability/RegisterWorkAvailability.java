package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
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
	 * [1] 提出する
	 * @param require
	 * @param workOneDay 一日分の勤務希望
	 * @param baseDate 基準日	
	 * @return
	 */
	public static AtomTask register(Require require, List<WorkAvailabilityOfOneDay> workOneDays, GeneralDate baseDate) {
		val workOneDay = workOneDays.get(0);
		val shiftRuleOpt = GetUsingShiftTableRuleOfEmployeeService.get(require, workOneDay.getEmployeeId(), baseDate);
		if (!shiftRuleOpt.isPresent()) {
			throw new BusinessException("Msg_2049");
		}

		val shiftRule = shiftRuleOpt.get();
		if (shiftRule.getUseWorkAvailabilityAtr() == NotUseAtr.NOT_USE) {
			throw new BusinessException("Msg_2052");
		}

		if (shiftRule.getShiftTableSetting().get().isOverDeadline(workOneDay.getWorkAvailabilityDate())) {
			throw new BusinessException("Msg_2050");
		}

		if (shiftRule.getShiftTableSetting().get().isOverHolidayMaxDays(workOneDays)) {
			throw new BusinessException("Msg_2051");
		}
		
		return AtomTask.of(() -> {
			require.deleteAll(workOneDays);
			require.insertAll(workOneDays);
		});
		
	}
	
	public static interface Require extends GetUsingShiftTableRuleOfEmployeeService.Require{
		
		/**
		 * [R-1] 一日分の勤務希望を追加する
		 * @param workOneDay
		 */
		void insertAll(List<WorkAvailabilityOfOneDay> workOneDays);
		
		/**
		 * [R-2] 一日分の勤務希望を削除する
		 * @param workOneDay
		 */
		void deleteAll(List<WorkAvailabilityOfOneDay> workOneDays);
		
	}
}
