package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.clock.ClockHourMinute;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * VO : 日時期間
 * 
 * @author tutk
 *
 */
public class DateAndTimePeriod implements DomainValue {

	/**
	 * 開始日時
	 */
	@Getter
	private final GeneralDateTime statDateTime;

	/**
	 * 終了日時
	 */
	@Getter
	private final GeneralDateTime endDateTime;

	public DateAndTimePeriod(GeneralDateTime statDateTime, GeneralDateTime endDateTime) {
		super();
		this.statDateTime = statDateTime;
		this.endDateTime = endDateTime;
	}

	/**
	 * [C-1] 1日範囲を求める
	 * 
	 * @param require
	 * @param employeeId
	 * @return
	 */
	public static DateAndTimePeriod calOneDayRange(Require require, String employeeId) {
		Optional<WorkingConditionItem> optWorkingConditionItem = require.findWorkConditionByEmployee(employeeId,
				GeneralDate.today());
		if (!optWorkingConditionItem.isPresent()) {
			throw new BusinessException("Msg_430");
		}

		Optional<WorkTimeCode> workTimeCode = optWorkingConditionItem.get().getWorkCategory().getWeekdayTime()
				.getWorkTimeCode();
		if (!workTimeCode.isPresent()) {
			throw new BusinessException("Msg_1142");
		}

		Optional<PredetemineTimeSetting> optPredetemineTimeSetting = require.findByWorkTimeCode(workTimeCode.get().v());
		if (!optPredetemineTimeSetting.isPresent()) {
			throw new BusinessException("Msg_1142");
		}

		TimeWithDayAttr startDateClock = optPredetemineTimeSetting.get().getStartDateClock();
		GeneralDate baseDate = GeneralDate.today();
		ClockHourMinute clockHourMinute = GeneralDateTime.now().clockHourMinute();
		if (clockHourMinute.v() < startDateClock.v()) {
			GeneralDateTime dateTime = GeneralDateTime.now().addDays(-1);
			GeneralDateTime statDateTime = GeneralDateTime
					.ymdhms(dateTime.year(), dateTime.month(), dateTime.day(), 0, 0, 0).addMinutes(startDateClock.v());
			GeneralDateTime endDateTime = GeneralDateTime.ymdhms(GeneralDateTime.now().year(),
					GeneralDateTime.now().month(), GeneralDateTime.now().day(), 0, 0, 0).addMinutes(startDateClock.v());
			return new DateAndTimePeriod(statDateTime, endDateTime);
		}
		GeneralDate date = baseDate.addDays(1);
		GeneralDateTime statDateTime = GeneralDateTime
				.ymdhms(baseDate.year(), baseDate.month(), baseDate.day(), 0, 0, 0).addMinutes(startDateClock.v());
		GeneralDateTime endDateTime = GeneralDateTime.ymdhms(date.year(), date.month(), date.day(), 0, 0, 0)
				.addMinutes(startDateClock.v());
		return new DateAndTimePeriod(statDateTime, endDateTime);
	}

	public static interface Require extends GetEmpStampDataService.Require {

		/**
		 * [R-1] 労働条件を取得する
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate);

		/**
		 * [R-2] 所定時間設定を取得する
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode);
	}

}
