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
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param datePeriod 期間
	 * @param workOneDay 一日分の勤務希望リスト
	 * @return.
	 */
	public static AtomTask register(
			Require require,
			String cid,
			String sid,
			DatePeriod datePeriod,
			List<WorkAvailabilityOfOneDay> workOneDays) {
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
		
		List<GeneralDate> workAvailabilityDates = workOneDays.stream()
				.map(WorkAvailabilityOfOneDay::getWorkAvailabilityDate)
				.collect(Collectors.toList());
		
		datePeriod.stream().forEach(date ->{
			if(shiftTableSetting.isOverDeadline(date)) {
				if(workAvailabilityDates.contains(date) || require.existWorkAvailabilityOfOneDay(sid, date)) {
					throw new BusinessException("Msg_2050");
				}
			}
		});
		
		if (shiftTableSetting.isOverHolidayMaxDays(require, cid, workOneDays)) {
			throw new BusinessException("Msg_2051");
		}
		
		return AtomTask.of(() -> {
			require.deleteAllWorkAvailabilityOfOneDay(sid, datePeriod);
			
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
		
		/**
		 * [R-3] 一日分の勤務希望が存在するか
		 * @param sid 社員ID
		 * @param date 年月日
		 * @return
		 */
		boolean existWorkAvailabilityOfOneDay(String sid, GeneralDate date);
	}
}
