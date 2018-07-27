package nts.uk.ctx.at.request.app.find.application.holidayshipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;

/**
 * @author sonnlb 勤務時間Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkingHoursDto {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	/**
	 * 終了時刻
	 */
	private Integer endTime;

	public static WorkingHoursDto createFromDomain(AbsenceLeaveWorkingHour workTime) {
		return new WorkingHoursDto(workTime.getStartTime().v(), workTime.getEndTime().v());
	}
}
