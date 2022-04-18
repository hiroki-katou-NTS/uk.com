package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.request.dom.application.annualholiday.ReNumAnnLeaReferenceDateExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaveImport;

import java.util.function.Function;

/**
 * 条件値チェック(社員別・休暇)
 */
@RequiredArgsConstructor
public enum ConditionValueVacationByEmployee implements ConditionValueLogic<ConditionValueVacationByEmployee.Context> {

	年休残数(1, "年休残数", c -> {
		return c.require.getAnnualLeaveRemain(c.employeeId, c.period.end()).getRemainingDays();
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

	@Value
	public static class Context implements ConditionValueContext {
		Require require;
		String employeeId;
		DatePeriod period;

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
		ReNumAnnLeaveImport getAnnualLeaveRemain(String employeeId, GeneralDate date);
	}
}
