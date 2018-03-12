package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppHolidayWorkPreAndReferDto {
	/**
	 * appDate
	 */
	private GeneralDate appDate;

	/**
	 * 残業申請時間設定
	 */
	private List<CaculationTime> holidayWorkInputs;

	/**
	 * workType
	 */
	private WorkTypeOvertime workType;
	
	/**
	 * workTimne
	 */
	private SiftType workTime;

	/**
	 * 勤務時間Start1
	 */
	private Integer workClockStart1;
	/**
	 * 勤務時間End1
	 */
	private Integer workClockEnd1;
	/**
	 * 勤務時間Start2
	 */
	private Integer workClockStart2;
	/**
	 * 勤務時間End2
	 */
	private Integer workClockEnd2;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer holidayWorkShiftNight;

}
