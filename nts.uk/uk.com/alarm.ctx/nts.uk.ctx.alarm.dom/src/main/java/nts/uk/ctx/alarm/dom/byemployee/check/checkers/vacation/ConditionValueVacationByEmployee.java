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
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaveImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaCriterialDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.shr.com.context.AppContexts;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

/**
 * 条件値チェック(社員別・休暇)
 */
@RequiredArgsConstructor
public enum ConditionValueVacationByEmployee implements ConditionValueLogic<ConditionValueVacationByEmployee.Context> {

	年休残数(1, "年休残数", c -> {
		return GetAnnLeaRemNumWithinPeriodProc.algorithm(
				c.require,
				new CacheCarrier(),
				AppContexts.user().companyId(),
				c.employeeId,
				c.period,
				InterimRemainMngMode.OTHER,
				c.period.end(),
				false,// 使ってないらしい、ゴミ
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty()
		).map(r -> r.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumberInfo().getRemainingNumber().getTotalRemainingDays().v())
				.orElse(null);
	}),

	積立年休残数(2, "積立年休残数", c -> {
		return c.require.getReserveLeaveRemain(c.employeeId, c.period.end()).map(r -> r.getRemainingDays()).orElse(null);
	}),

	振休残数(3, "振休残数", c -> {
		return AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(c.require, new CacheCarrier(), c.employeeId, c.period.end()).v();
	}),

	代休残数(4, "代休残数", c -> {
		return getSubHoliday(c).getRemainDay().v();
	}),

	時間代休残数(5, "時間代休残数", c -> {
		return getSubHoliday(c).getRemainTime().v().doubleValue();
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

	// 代休残数を取得する
	private static SubstituteHolidayAggrResult getSubHoliday(Context context){
		return NumberRemainVacationLeaveRangeQuery.getBreakDayOffMngInPeriod(
			context.require,
			new BreakDayOffRemainMngRefactParam(
					AppContexts.user().companyId(),
					context.employeeId,
					context.period,
					false,
					context.period.end(),
					false,
					Collections.emptyList(),
					Optional.empty(),
					Optional.empty(),
					Optional.empty(),
					new FixedManagementDataMonth(
							Collections.emptyList(),
							Collections.emptyList()
					)
			));
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

	public interface Require extends
			GetAnnLeaRemNumWithinPeriodProc.RequireM3,
			AbsenceReruitmentMngInPeriodQuery.RequireM11,
			NumberRemainVacationLeaveRangeQuery.Require {

		Optional<RsvLeaCriterialDate> getReserveLeaveRemain(String employeeId, GeneralDate date);
	}
}
