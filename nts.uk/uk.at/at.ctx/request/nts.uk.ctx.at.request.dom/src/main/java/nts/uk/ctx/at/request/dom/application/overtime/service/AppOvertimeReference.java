package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppOvertimeReference {
	/**
	 * appDate
	 */
	private String appDateRefer;

	/**
	 * 残業申請時間設定
	 */
	private List<CaculationTime> overTimeInputsRefer;

	/**
	 * workType
	 */
	private WorkTypeOvertime workTypeRefer;
	
	/**
	 * siftType
	 */
	private SiftType siftTypeRefer;

	private String workClockFromTo1Refer;
	
	private String workClockFromTo2Refer;
	
	/**
	 * フレックス超過時間
	 */
	private Integer flexExessTimeRefer;
	/**
	 * 就業時間外深夜時間
	 */
	private Integer overTimeShiftNightRefer;
}
