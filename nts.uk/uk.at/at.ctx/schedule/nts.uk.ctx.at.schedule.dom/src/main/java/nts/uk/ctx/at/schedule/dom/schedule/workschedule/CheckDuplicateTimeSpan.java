package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.val;
import nts.arc.time.GeneralDate;
import java.util.Optional;

public class CheckDuplicateTimeSpan {

	public static boolean checkDuplicatePreviousAndNext(Require require, String employeeId, GeneralDate date) {
		val optBaseSche = require.getWorkSchedule(employeeId, date);
		if(optBaseSche.isPresent()) {
			val optBaseSpan = optBaseSche.get().getOptTimeLeaving();
			if (optBaseSpan.isPresent()) {
				// 前日のスケジュールとのチェック
				if (checkDuplicatePreviousDay(require, optBaseSche.get())) {
					return true;
				}
				// 翌日のスケジュールとのチェック
				if (checkDuplicateNextDay(require, optBaseSche.get())) {
					return true;
				}
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
