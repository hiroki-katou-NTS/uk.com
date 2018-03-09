package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 振出勤務時間
 * 
 * @author sonnlb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceLeaveWorkingHour {
	/**
	 * 開始時刻
	 */
	private WorkTime startTime;
	/**
	 * 終了時刻
	 */
	private WorkTime endTime;
}
