package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.AttendRateAtNextHoliday;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RCAnnualHolidayManagement;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * 固定のチェック条件(社員別・休暇)
 */
@RequiredArgsConstructor
public enum FixedLogicVacationByEmployee {

	子の看護休暇上限(1, c -> {
		val result = new ArrayList<AlarmRecordByEmployee>();
		val errorList = GetRemainingNumberChildCareService.getChildCareRemNumWithinPeriod(
				AppContexts.user().companyId(),
				c.employeeId,
				c.period,
				InterimRemainMngMode.OTHER,
				c.period.end(),
				Optional.empty(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty(), new CacheCarrier(),	c.require)
				.getChildCareNurseErrors();

		return () -> errorList.stream()
				.map(e -> c.alarm(e.getYmd()))
				.iterator();
	}),

	介護休暇上限(2, c -> {
		val result = new ArrayList<AlarmRecordByEmployee>();
		val errorList = GetRemainingNumberCareService.getCareRemNumWithinPeriod(
						AppContexts.user().companyId(),
						c.employeeId,
						c.period,
						InterimRemainMngMode.OTHER,
						c.period.end(),
						Optional.empty(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty(), new CacheCarrier(), c.require)
				.getChildCareNurseErrors();

		return () -> errorList.stream()
				.map(e -> c.alarm(e.getYmd()))
				.iterator();
	}),

	;

	public final int value;

	private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

	/**
	 * チェックする
	 * @param require
	 * @param employeeId
	 * @param message
	 * @return
	 */
	public Iterable<AlarmRecordByEmployee> check(
			RequireCheck require,
			String employeeId,
			DatePeriod period,
			AlarmListAlarmMessage message) {

		val context = new Context(require, employeeId, period, message);

		return logic.apply(context);
	}

	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		DatePeriod period;
		AlarmListAlarmMessage message;

		public AlarmRecordByEmployee alarm() {
			return new AlarmRecordByEmployee(
					employeeId,
					DateInfo.none(),
					AlarmListCategoryByEmployee.VACATION,
					"",
					"",
					"",
					message);
		}

		public AlarmRecordByEmployee alarm(GeneralDate date) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(date),
					AlarmListCategoryByEmployee.VACATION,
					"",
					"",
					"",
					message);
		}
	}

	public interface RequireCheck extends GetRemainingNumberChildCareNurseService.Require {

	}
}
