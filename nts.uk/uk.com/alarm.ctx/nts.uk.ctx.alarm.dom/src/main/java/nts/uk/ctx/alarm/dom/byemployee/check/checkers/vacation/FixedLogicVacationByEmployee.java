package nts.uk.ctx.alarm.dom.byemployee.check.checkers.vacation;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.GetRemainingNumberPublicHolidayService;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.PublicHolidayInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.shr.com.context.AppContexts;

import java.util.*;
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

	公休残数(3, c -> {
		val result = new ArrayList<AlarmRecordByEmployee>();
		val publicHolidayInfo = getPublicHolidayInfo(c);
		return () -> publicHolidayInfo.stream()
				.filter(phi -> phi.getPublicHolidayDigestionInformation().getRemainingDayNumber().v() > 0)
				.map(phi -> c.alarm(phi.getYearMonth()))
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

	// 公休情報を取得する
	private static List<PublicHolidayInformation> getPublicHolidayInfo(Context context){
		return GetRemainingNumberPublicHolidayService.getPublicHolidayRemNumWithinPeriod(
				AppContexts.user().companyId(),
				context.employeeId,
				// 一旦期間終了日の年月
				// スケ集計ブランチで実装済、マージされたら差し替える
				Arrays.asList(context.period.end().yearMonth()),
				context.period.end(),
				InterimRemainMngMode.OTHER,
				Optional.empty(),
				Collections.emptyList(),
				Optional.empty(),
				Optional.empty(),
				new CacheCarrier(),
				context.require)
				.publicHolidayInformation;
	}



	@Value
	private class Context {
		RequireCheck require;
		String employeeId;
		DatePeriod period;
		AlarmListAlarmMessage message;

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

		public AlarmRecordByEmployee alarm(YearMonth yearMonth) {
			return new AlarmRecordByEmployee(
					employeeId,
					new DateInfo(yearMonth),
					AlarmListCategoryByEmployee.VACATION,
					"",
					"",
					"",
					message);
		}
	}

	public interface RequireCheck extends
			GetRemainingNumberChildCareNurseService.Require,
			GetRemainingNumberPublicHolidayService.RequireM1 {

	}
}
