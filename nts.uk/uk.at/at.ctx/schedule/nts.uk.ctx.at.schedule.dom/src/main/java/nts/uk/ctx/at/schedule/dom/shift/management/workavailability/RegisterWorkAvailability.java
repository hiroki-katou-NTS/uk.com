package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetUsingShiftTableRuleOfEmployeeService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRule;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * 勤務希望を登録する
 * @author lan_lt
 *
 */
public class RegisterWorkAvailability {
	
	/**
	 * 登録する
	 * @param require
	 * @param sid 社員ID
	 * @param datePeriod 期間
	 * @param workOneDay 一日分の勤務希望リスト
	 * @return
	 */
	public static AtomTask register(Require require, String sid, DatePeriod datePeriod, List<WorkAvailabilityOfOneDay> workOneDays) {
		val shiftRuleOpt = GetUsingShiftTableRuleOfEmployeeService.get(require, sid, datePeriod.end());
		if (!shiftRuleOpt.isPresent()) {
			throw new BusinessException("Msg_2049");
		}

		val shiftRule = shiftRuleOpt.get();
		if (shiftRule.getUseWorkAvailabilityAtr() == NotUseAtr.NOT_USE) {
			throw new BusinessException("Msg_2052");
		}

		// 勤務希望運用区分 == する場合は、必ず「シフト表の設定」emptyではないため。
		val shiftTableSetting = shiftRule.getShiftTableSetting().get();
		val deadlineAndPeriod = shiftTableSetting.getCorrespondingDeadlineAndPeriod(GeneralDate.today());
		
		if (deadlineAndPeriod.getDeadline().before(datePeriod.start())) {
			throw new BusinessException("Msg_2050");
		}
		
		List<WorkAvailabilityOfOneDay> outDeadlines = workOneDays.stream()
				.filter(c -> c.getWorkAvailabilityDate().after(deadlineAndPeriod.getDeadline()))
				.collect(Collectors.toList());

		if(!CollectionUtil.isEmpty(outDeadlines)) {
			throw new BusinessException("Msg_2050");
		}
		
		if (shiftTableSetting.isOverHolidayMaxDays(require, workOneDays)) {
			throw new BusinessException("Msg_2051");
		}
		
		val datePeriodEnd = datePeriod.end().before(deadlineAndPeriod.getDeadline()) ? datePeriod.end()
				: deadlineAndPeriod.getDeadline();
		
		val targetPeriodRegister = new DatePeriod(datePeriod.start(), datePeriodEnd);
		
		return AtomTask.of(() -> {
			require.deleteAllWorkAvailabilityOfOneDay(sid, targetPeriodRegister);
			
			if(!CollectionUtil.isEmpty(workOneDays)) {
				require.insertAllWorkAvailabilityOfOneDay(workOneDays);
			}
			
		});
		
	}
	
	public static interface Require extends GetUsingShiftTableRuleOfEmployeeService.Require
											, WorkAvailabilityOfOneDay.Require
											, WorkAvailabilityRule.Require{
		
		/**
		 * [R-1] 一日分の勤務希望を追加する
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
