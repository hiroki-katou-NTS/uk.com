package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sonnlb 勤務時間Dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AbsenceLeaveWorkingHourDto {
	/**
	 * 開始時刻
	 */
	private Integer startTime;
	/**
	 * 終了時刻
	 */
	private Integer endTime;
}
