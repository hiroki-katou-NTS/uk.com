package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * 
 * @author HieuLT
 *
 */
/** 個人勤務日区分別勤務 **/
@Getter
public class WorkByIndividualWorkDay extends DomainObject{

	/** 勤務時間帯 -- 個人勤務日区分別勤務時間  - update : chuyển PersonalDayOfWeek vào trong đây*/
	private PersonalWorkCategory workTime;
	/** 勤務種類--- 個人勤務日区分別勤務種類  -- tao moi**/
	private WorkTypeByIndividualWorkDay workType;

	public WorkByIndividualWorkDay(PersonalWorkCategory workTime, WorkTypeByIndividualWorkDay workType) {
		super();
		this.workTime = workTime;
		this.workType = workType;
	}

	/**
	 * [1] 該当する曜日の勤務情報を取得する
	 * 
	 * @param date
	 * @return
	 */
	public WorkInformation getWorkInformationDayOfTheWeek(GeneralDate date) {
		// val 曜日 ＝ 年月日。曜日；
		int dayOfWeek = date.dayOfWeek();
		// val 就業時間帯コード ＝ ＠勤務時間帯。曜日別。該当の単一日勤務予定を取得する（曜日）：
		// map $。就業時間帯コード WorkTimeCode
		Optional<SingleDaySchedule> data = this.workTime.getDayOfWeek().getSingleDaySchedule(date);
		if (data.isPresent()) {
			return new WorkInformation(workType.getWeekdayTimeWTypeCode(),
					data.get().getWorkTimeCode().isPresent() ? data.get().getWorkTimeCode().get() : null);

		} else {
			return new WorkInformation(workType.getHolidayTimeWTypeCode(), null);
		}
	}

	/**
	 * [2] 休日の勤務情報を取得する
	 * 
	 * @return
	 */
	public WorkInformation getHolidayWorkInformation() {

		//return new 勤務情報（＠勤務種類。休日時、Optinal。empty()）
		return new WorkInformation(workType.getHolidayTimeWTypeCode(), null);

	}

	/**
	 * [3] 出勤日の勤務情報を取得する
	 * 
	 * @return
	 */
	public WorkInformation getWorkInformationWorkDay() {

		// return new 勤務情報（＠勤務種類。出勤時、＠勤務時間帯。平日時。就業時間帯コード）
		return new WorkInformation(workType.getWeekdayTimeWTypeCode(), workTime.getWeekdayTime().getWorkTimeCode().get());

	}
}
