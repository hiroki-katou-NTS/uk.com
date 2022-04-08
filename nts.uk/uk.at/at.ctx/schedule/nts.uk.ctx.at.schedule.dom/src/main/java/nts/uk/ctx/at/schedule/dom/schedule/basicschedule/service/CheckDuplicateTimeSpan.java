package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckDuplicateTimeSpan {

	public static boolean checkDuplicatePreviousAndNext(Require require, BasicSchedule baseSche) {
		// 前日のスケジュールとのチェック
		if(checkDuplicatePreviousDay(require, baseSche)){
			return true;
		}
		// 翌日のスケジュールとのチェック
		if(checkDuplicateNextDay(require, baseSche)){
			return true;
		}
		return false;
	}

	/**
	 * 前日のスケジュールの時間帯と重複しているかチェックする
	 * @param require
	 * @param baseSche
	 * @return
	 */
	private static boolean checkDuplicatePreviousDay(Require require, BasicSchedule baseSche){
		val optPreviousSche = require.getBasicSchedule(baseSche.getEmployeeId(), baseSche.getDate().addDays(-1));
		if(optPreviousSche.isPresent()) {
			val baseSpan = maxSpan(baseSche.getWorkScheduleTimeZones());
			// 前日の時間帯なので24時間分過去にずらす
			val previousSpan = maxSpan(optPreviousSche.get().getWorkScheduleTimeZones()).shiftBack(1440);
			return baseSpan.getDuplicatedWith(previousSpan).isPresent();
		}
		return false;
	}

	/**
	 * 翌日のスケジュールの時間帯と重複しているかチェックする
	 * @param require
	 * @param baseSche
	 * @return
	 */
	private static boolean checkDuplicateNextDay(Require require, BasicSchedule baseSche){
		val optNextSche = require.getBasicSchedule(baseSche.getEmployeeId(), baseSche.getDate().addDays(1));
		if(optNextSche.isPresent()) {
			val baseSpan = maxSpan(baseSche.getWorkScheduleTimeZones());
			// 翌日の時間帯なので24時間分未来にずらす
			val nextSpan = maxSpan(optNextSche.get().getWorkScheduleTimeZones()).shiftAhead(1440);
			return baseSpan.getDuplicatedWith(nextSpan).isPresent();
		}
		return false;
	}

	/**
	 * 複数の時間帯の最大期間を求める
	 * @param timeZoneList
	 * @return
	 */
	private static TimeSpanForCalc maxSpan(List<WorkScheduleTimeZone> timeZoneList){
		Map<Integer, WorkScheduleTimeZone> baseMap = timeZoneList.stream()
				.collect(Collectors.toMap(tz -> tz.getScheduleCnt(), tz -> tz));
		// 1回目の開始時刻　～　最終回目の終了時刻
		return new TimeSpanForCalc(baseMap.get(1).getScheduleStartClock(),baseMap.get(baseMap.size()).getScheduleEndClock());
	}

	public interface Require{
		Optional<BasicSchedule> getBasicSchedule(String employeeId, GeneralDate date);
	}
}
