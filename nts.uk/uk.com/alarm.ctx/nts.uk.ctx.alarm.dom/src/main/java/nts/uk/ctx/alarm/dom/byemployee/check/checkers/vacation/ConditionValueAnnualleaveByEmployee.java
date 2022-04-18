package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.AttendRateAtNextHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveNumberInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedPercent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック(社員別・年休付与)
 */
@RequiredArgsConstructor
public enum ConditionValueAnnualleaveByEmployee implements ConditionValueLogic<ConditionValueAnnualleaveByEmployee.Context> {

	出勤率(1, "年休付与用出勤率", c -> {
		return c.require.getDaysPerYear(c.employeeId)
				.map(ar -> ar.getAttendanceRate().v().doubleValue())
				.orElse(null);
	}),

	年休消化率_繰越含む(2, "年休消化率（繰越含む）", c -> {
		return c.require.getLast(c.employeeId)
				.map(rd -> getUsePercentIncludesCarryOver(rd.getDetails()).v().doubleValue())
				.orElse(null);
	}),

	年休消化率_繰越含まない(2, "年休消化率（繰越含まない）", c -> {
		return c.require.getLast(c.employeeId)
				.map(rd -> rd.getDetails().getUsedPercent().v().doubleValue())
				.orElse(null);
	}),

	;

	public final int value;

	/** 項目名 */
	@Getter
	private final String name;

	private final Function<Context, Double> getValue;

	@Override
	public Double getValue(Context context) {
		return getValue.apply(context);
	}

	/**
	 * 年休使用率を求める（繰越分を含む）
	 * @param leaveNumberInfo
	 * @return
	 */
	private static LeaveUsedPercent getUsePercentIncludesCarryOver(LeaveNumberInfo leaveNumberInfo){
		val remainingNumber = leaveNumberInfo.getRemainingNumber();
		if(remainingNumber.getDays().v().equals(0.0)){
			return new LeaveUsedPercent(new BigDecimal(0));
		}
		leaveNumberInfo.getUsedNumber();
		return new LeaveUsedPercent(new BigDecimal(new DecimalFormat("#.#").format((
						leaveNumberInfo.getUsedNumber().getDays().v()
								/ remainingNumber.getDays().v())*100.0)));
	}

	@Value
	public static class Context implements ConditionValueContext {
		Require require;
		String employeeId;

		@Override
		public AlarmListCategoryByEmployee getCategory() {
			return AlarmListCategoryByEmployee.VACATION;
		}

		@Override
		public String getEmployeeId() {
			return employeeId;
		}

		@Override
		public DateInfo getDateInfo() {
			return new DateInfo(GeneralDate.today());
		}
	}

	public interface Require{
		Optional<AttendRateAtNextHoliday> getDaysPerYear(String employeeId);

		Optional<AnnualLeaveGrantRemainingData> getLast(String employeeId);
	}
}
