package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectattdclock;

import lombok.AllArgsConstructor;

/**
 * 出退勤区分
 * @author tutk
 *
 */
@AllArgsConstructor
public enum AttendanceAtr {
	/**
	 * 出勤
	 */
	GOING_TO_WORK,
	/**
	 * 退勤
	 */
	LEAVING_WORK,
	/**
	 * 外出
	 */
	GO_OUT,
	/**
	 * 臨時
	 */
	TEMPORARY;
	
}
