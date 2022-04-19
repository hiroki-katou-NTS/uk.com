package nts.uk.ctx.alarm.dom.byemployee.check.checkers.schedule.daily.multi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.errorcount.ErrorAlarmCounter;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.multi.multiday.ExecuteCheckErrorAlarmDaily;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodDaily;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.fixedlogic.FixedLogicSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * アラームリストのチェック条件(社員別・スケジュール複数日)
 */
@AllArgsConstructor
@Getter
public class ScheduleMultiDailyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

	/** 会社ID */
	private final String companyId;

	/** コード */
	private final AlarmListCheckerCode code;

	/** エラーアラームの発生カウント */
	private List<ErrorAlarmCounter<CheckScheduleMultiDaily, GeneralDate>> continueCheck;

	/**
	 * チェックする
	 * @param require
	 * @param context
	 * @return
	 */
	@Override
	public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
		return check(require, context);
	}
	public Iterable<AlarmRecordByEmployee> check(RequireCheck require, CheckingContextByEmployee context) {
		String employeeId = context.getTargetEmployeeId();
		val period = context.getCheckingPeriod().getDaily().calculatePeriod(require, employeeId);

		return checkErrorAlarmCount(require, employeeId, period);
	}

	private Iterable<AlarmRecordByEmployee> checkErrorAlarmCount(RequireCheck require, String employeeId, DatePeriod period) {

		BiFunction<GeneralDate, GeneralDate, DateInfo> periodToDateInfo = (start, end) -> {
			return new DateInfo(new DatePeriod(start, end));
		};

		Function<CheckScheduleMultiDaily, Optional<String>> getErrorAlarmName = c -> {
			return Optional.of(c.name());
		};

		Function<CheckScheduleMultiDaily, Iterable<GeneralDate>> errorAlarmChecker = c -> {
			return c.check(require, employeeId, period);
		};

		return IteratorUtil.iterableFlatten(
				continueCheck,
				c -> c.check(
						employeeId,
						period,
						AlarmListCategoryByEmployee.SCHEDULE_MULTI_DAY,
						getErrorAlarmName,
						periodToDateInfo,
						errorAlarmChecker));
	}

	public interface RequireCheck extends
			CheckScheduleMultiDaily.Require,
			CheckingPeriodDaily.Require{
	}
}
