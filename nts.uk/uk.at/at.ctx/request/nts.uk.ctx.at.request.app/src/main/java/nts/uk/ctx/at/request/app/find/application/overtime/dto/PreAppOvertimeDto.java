package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreAppOvertimeDto {
	/**
	 * appDate
	 */
	private String appDatePre;

	/**
	 * 残業申請時間設定
	 */
	private List<OvertimeInputDto> overTimeInputsPre;

	/**
	 * workType
	 */
	private WorkTypeOvertime workTypePre;
	
	/**
	 * siftType
	 */
	private SiftType siftTypePre;

	/**
	 * 勤務時間From1
	 */
	private Integer workClockFrom1Pre;
	/**
	 * 勤務時間To1
	 */
	private Integer workClockTo1Pre;
	/**
	 * 勤務時間From2
	 */
	private Integer workClockFrom2Pre;
	/**
	 * 勤務時間To2
	 */
	private Integer workClockTo2Pre;
	
	/**
	 * フレックス超過時間
	 */
	private Integer flexExessTimePre;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer overTimeShiftNightPre;

}
