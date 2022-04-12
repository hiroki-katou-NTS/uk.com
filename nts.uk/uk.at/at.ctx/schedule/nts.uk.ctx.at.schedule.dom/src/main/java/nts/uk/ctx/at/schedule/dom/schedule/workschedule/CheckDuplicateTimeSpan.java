package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckDuplicateTimeSpan {

	public static boolean checkDuplicatePreviousAndNext(Require require, WorkSchedule baseSche) {
		val optBaseSpan = baseSche.getOptTimeLeaving();
		if(optBaseSpan.isPresent()) {
			// 前日のスケジュールとのチェック
			if (checkDuplicatePreviousDay(require, baseSche)) {
				return true;
			}
			// 翌日のスケジュールとのチェック
			if (checkDuplicateNextDay(require, baseSche)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 前日のスケジュールの時間帯と重複しているかチェックする
	 * @param require
	 * @param baseSche
	 * @return
	 */
	private static boolean checkDuplicatePreviousDay(Require require, WorkSchedule baseSche){
		val optPreviousSche = require.getWorkSchedule(baseSche.getEmployeeID(), baseSche.getYmd().addDays(-1));
		if(optPreviousSche.isPresent()) {
			val optBaseSpan = baseSche.getOptTimeLeaving().get().getMaxSpan();
			val optPreviousSpan = optPreviousSche.get().getOptTimeLeaving().get().getMaxSpan();
			if(optBaseSpan.isPresent() && optPreviousSpan.isPresent()){
				// 前日の時間帯なので24時間分過去にずらす
				return optBaseSpan.get().getDuplicatedWith(optPreviousSpan.get().shiftBack(1440)).isPresent();
			}
		}
		return false;
	}

	/**
	 * 翌日のスケジュールの時間帯と重複しているかチェックする
	 * @param require
	 * @param baseSche
	 * @return
	 */
	private static boolean checkDuplicateNextDay(Require require, WorkSchedule baseSche){
		val optNextSche = require.getWorkSchedule(baseSche.getEmployeeID(), baseSche.getYmd().addDays(1));
		if(optNextSche.isPresent()) {
			val optBaseSpan = baseSche.getOptTimeLeaving().get().getMaxSpan();
			val optNextSpan = optNextSche.get().getOptTimeLeaving().get().getMaxSpan();
			if(optBaseSpan.isPresent() && optNextSpan.isPresent()){
				// 翌日の時間帯なので24時間分未来にずらす
				return optBaseSpan.get().getDuplicatedWith(optNextSpan.get().shiftAhead(1440)).isPresent();
			}
		}
		return false;
	}

	public interface Require{
		Optional<WorkSchedule> getWorkSchedule(String employeeId, GeneralDate date);
	}
}
