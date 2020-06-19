package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.TimeOfMonthly;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;

/**
 * DS : 抑制する打刻種類を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.抑制する打刻種類を取得する
 * 
 * @author tutk
 *
 */
@Stateless
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
		// if not [prv-1] 打刻ボタンを抑制するか(require, 打刻手段)
		if (!checkOffStampButton(require, stampMeans)) {
			// return 抑制する打刻#[C-1] 全ての打刻を強調しない()
			return StampToSuppress.allStampFalse();
		}

		DateAndTimePeriod dateAndTimePeriod = DateAndTimePeriod.calOneDayRange(require, employeeId);

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
		if (stamp.isPresent()) {
			checkGoOut = stamp.get().getType().getChangeClockArt() == ChangeClockArt.GO_OUT;
		}

		boolean goingToWork = checkIsWork; // 出勤
		boolean departure = !checkIsWork; // 退勤
		boolean goOut = !(checkIsWork && !checkGoOut); // 外出
		boolean turnBack = !(checkIsWork && checkGoOut); // 戻り

		return new StampToSuppress(goingToWork, departure, goOut, turnBack);
	}

	public static interface Require extends DateAndTimePeriod.Require {

		/**
		 * [R-1] 労働条件を取得する
		 * 
		 * アルゴリズム.社員の労働条件を取得する(社員ID, 基準日)
		 * 
		 * @param companyId
		 * @return
		 */
		List<TimeOfMonthly> findByDate(String companyId, GeneralDate criteriaDate);

		Optional<StampSettingPerson> getStampSet();

		/**
		 * [R-2] 所定時間設定を取得する
		 * 
		 * 所定時間設定Repository.取得する(就業時間帯コード)
		 * 
		 * @param companyId
		 * @return
		 */
	}

}
