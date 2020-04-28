package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

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
		//	if not [prv-1] 打刻ボタンを抑制するか(require, 打刻手段)	
		if (!checkOffStampButton(require, stampMeans)) {
			return StampToSuppress.allStampFalse();
		}
		
		//	$日時期間 = 日時期間#1日範囲を求める(require, 社員ID)
		DateAndTimePeriod dateAndTimePeriod = DateAndTimePeriod.calOneDayRange(require, employeeId);

		//	$打刻リスト = [prv-2] 打刻データを取得する(require, 社員ID, 日時期間)
		List<Stamp> listStamp = getDataStamp(require, employeeId, dateAndTimePeriod);
		
		// return [prv-3] 抑制する打刻を判断する($打刻リスト)
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
	 * [prv-2] 打刻データを取得する
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
	 * [prv-3] 抑制する打刻を判断する
	 * 
	 * @param listStamp
	 * @return
	 */
	private static StampToSuppress judgmentStampToSuppress(List<Stamp> listStamp) {

		if (listStamp.isEmpty()) {
			return StampToSuppress.highlightAttendance();
		}

		boolean checkIsWork = listStamp.stream()
				.anyMatch(c -> c.getType().getChangeClockArt() == ChangeClockArt.GOING_TO_WORK);

		Optional<Stamp> stamp = listStamp.stream()
				.filter(c -> c.getType().getChangeClockArt() == ChangeClockArt.GO_OUT
						|| c.getType().getChangeClockArt() == ChangeClockArt.RETURN)
				.sorted((x, y) -> y.getStampDateTime().compareTo(x.getStampDateTime())).findFirst();
		boolean checkGoOut = false;
		if(stamp.isPresent()) {
			checkGoOut = stamp.get().getType().getChangeClockArt() == ChangeClockArt.GO_OUT;
		}

		boolean goingToWork = checkIsWork; // 出勤
		boolean departure = !checkIsWork; // 退勤
		boolean goOut = !(checkIsWork && !checkGoOut); // 外出
		boolean turnBack = !(checkIsWork && checkGoOut); // 戻り

		return new StampToSuppress(goingToWork, departure, goOut, turnBack);
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
