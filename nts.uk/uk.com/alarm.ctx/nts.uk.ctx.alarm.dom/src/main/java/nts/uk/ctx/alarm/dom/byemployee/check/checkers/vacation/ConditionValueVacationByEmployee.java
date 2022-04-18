package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueContext;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueLogic;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaveImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaCriterialDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;

import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック(社員別・休暇)
 */
@RequiredArgsConstructor
public enum ConditionValueVacationByEmployee implements ConditionValueLogic<ConditionValueVacationByEmployee.Context> {

	年休残数(1, "年休残数", c -> {
		return c.require.getAnnualLeaveRemain(c.employeeId, c.period.end()).getRemainingDays();
	}),

	積立年休残数(2, "積立年休残数", c -> {
		return AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(c.require, new CacheCarrier(), c.employeeId, c.period.end()).v();
	}),

	振休残数(3, "振休残数", c -> {
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

	public interface Require extends AbsenceReruitmentMngInPeriodQuery.RequireM11 {
		ReNumAnnLeaveImport getAnnualLeaveRemain(String employeeId, GeneralDate date);

		Optional<RsvLeaCriterialDate> getReserveLeaveRemain(String employeeId, GeneralDate date);
	}
}
