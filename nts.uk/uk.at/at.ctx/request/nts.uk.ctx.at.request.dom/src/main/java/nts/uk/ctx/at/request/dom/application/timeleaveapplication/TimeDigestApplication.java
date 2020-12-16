package nts.uk.ctx.at.request.dom.application.timeleaveapplication;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

import java.util.Optional;

/**
 * 時間消化申請
 */
@Getter
@Setter
@AllArgsConstructor
public class TimeDigestApplication {

	/**
	 * 60H超休
	 */
	private AttendanceTime sixtyHOvertime;

	/**
	 * 介護時間
	 */
	private AttendanceTime nursingTime;

	/**
	 * 子の看護時間
	 */
	private AttendanceTime childNursingTime;

	/**
	 * 時間代休時間
	 */
	private AttendanceTime hoursOfSubHoliday;

	/**
	 * 時間年休時間
	 */
	private AttendanceTime hoursOfHoliday;

	/**
	 * 時間特別休暇
	 */
	private AttendanceTime TimeSpecialVacation;

	/**
	 * 特別休暇枠NO
	 */
	private Optional<SpecialHdFrameNo> specialHdFrameNo;
}
