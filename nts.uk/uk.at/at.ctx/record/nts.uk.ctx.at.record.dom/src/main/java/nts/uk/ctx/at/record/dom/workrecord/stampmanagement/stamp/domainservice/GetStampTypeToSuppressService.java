package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.time.ClockHourMinute;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * DS : 抑制する打刻種類を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.抑制する打刻種類を取得する
 * 
 * @author tutk
 *
 */
public class GetStampTypeToSuppressService {

	/**
	 * [1] 取得する
	 * 
	 * @param require
	 * @param employeeId
	 * @param stampMeans
	 * @return
	 */
	public static StampToSuppress get(Require require, String employeeId, StampMeans stampMeans) {
		if (!checkOffStampButton(require, stampMeans)) {
			return StampToSuppress.allStampFalse();
		}
		DateAndTimePeriod dateAndTimePeriod = calOneDayRange(require, employeeId);

		List<Stamp> listStamp = getDataStamp(require, employeeId, dateAndTimePeriod);

		return judgmentStampToSuppress(listStamp);
	}

	/**
	 * [prv-1] 打刻ボタンを抑制するか
	 * 
	 * @param require
	 * @param stampMeans
	 * @return
	 */
	private static boolean checkOffStampButton(Require require, StampMeans stampMeans) {
		if (!stampMeans.checkIndivition()) {
			return false;
		}
		Optional<StampSettingPerson> optStampSettingPerson = require.getStampSet();
		if (!optStampSettingPerson.isPresent()) {
			return false;
		}

		return optStampSettingPerson.get().isButtonEmphasisArt();
	}

	/**
	 * [prv-2] 1日範囲を求める
	 * 
	 * @param require
	 * @param employeeId
	 * @return
	 */
	private static DateAndTimePeriod calOneDayRange(Require require, String employeeId) {
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

	/**
	 * [prv-3] 打刻データを取得する
	 * 
	 * @param require
	 * @param employeeId
	 * @param dateAndTimePeriod
	 * @return
	 */
	private static List<Stamp> getDataStamp(Require require, String employeeId, DateAndTimePeriod dateAndTimePeriod) {
		List<Stamp> listStamp = new ArrayList<>();

		GeneralDate startDate = GeneralDate.ymd(dateAndTimePeriod.getStatDateTime().year(),
				dateAndTimePeriod.getStatDateTime().month(), dateAndTimePeriod.getStatDateTime().day());
		Optional<StampDataOfEmployees> optStampDataOfEmployeesByStartDate = GetEmpStampDataService.get(require,
				employeeId, startDate);
		if (optStampDataOfEmployeesByStartDate.isPresent()) {
			listStamp.addAll(optStampDataOfEmployeesByStartDate.get().getListStamp().stream()
					.filter(c -> c.getStampDateTime().afterOrEquals(dateAndTimePeriod.getStatDateTime()))
					.collect(Collectors.toList()));
		}
		GeneralDate endDate = GeneralDate.ymd(dateAndTimePeriod.getEndDateTime().year(),
				dateAndTimePeriod.getEndDateTime().month(), dateAndTimePeriod.getEndDateTime().day());
		Optional<StampDataOfEmployees> optStampDataOfEmployeesByEndDate = GetEmpStampDataService.get(require,
				employeeId, endDate);
		if (optStampDataOfEmployeesByEndDate.isPresent()) {
			listStamp.addAll(optStampDataOfEmployeesByEndDate.get().getListStamp().stream()
					.filter(c -> c.getStampDateTime().afterOrEquals(dateAndTimePeriod.getStatDateTime()))
					.collect(Collectors.toList()));
		}
		return listStamp;
	}
	
	/**
	 * [prv-4] 抑制する打刻を判断する
	 * 
	 * @param listStamp
	 * @return
	 */
	private static StampToSuppress judgmentStampToSuppress(List<Stamp> listStamp) {

		Optional<Stamp> oStamp = listStamp.stream()
				.filter(c -> c.getType().getChangeClockArt() == ChangeClockArt.GOING_TO_WORK
						|| c.getType().getChangeClockArt() == ChangeClockArt.GO_OUT
						|| c.getType().getChangeClockArt() == ChangeClockArt.RETURN
						|| c.getType().getChangeClockArt() == ChangeClockArt.WORKING_OUT)
				.sorted((x, y) -> y.getStampDateTime().compareTo(x.getStampDateTime()))
				.findFirst();
		
		if(!oStamp.isPresent()) {
			return StampToSuppress.highlightAttendance();
		}
		
		Stamp stamp = oStamp.get();
		
		if(stamp.getType().getChangeClockArt() == ChangeClockArt.GOING_TO_WORK || stamp.getType().getChangeClockArt() == ChangeClockArt.RETURN) {
			return new StampToSuppress(true, false, false, true);
		}
		
		if(stamp.getType().getChangeClockArt() == ChangeClockArt.GO_OUT) {
			return new StampToSuppress(true, true, true, false);
		}

		return new StampToSuppress(true, true, true, true);
	}

	public static interface Require extends GetEmpStampDataService.Require {

		/**
		 * [R-1] 個人利用の打刻設定 StampSetPerRepository
		 * 
		 * @param companyId
		 * @return
		 */
		Optional<StampSettingPerson> getStampSet();

		/**
		 * [R-2] 労働条件を取得する WorkingConditionService
		 * 
		 * @param employeeId
		 * @param baseDate
		 * @return
		 */
		Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate);

		/**
		 * [R-3] 所定時間設定を取得する PredetemineTimeSettingRepository
		 * 
		 * @param companyId
		 * @param workTimeCode
		 * @return
		 */
		Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode);
	}

}
