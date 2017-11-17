package nts.uk.ctx.at.request.dom.application.common.adapter.frame;

import lombok.Data;

@Data
public class OvertimeInputCaculation {
	/**
	 * 勤怠種類
	 */
	private int attendanceID;
	/**
	 * 勤怠項目NO
	 */
	private int frameNo;
	
	private int resultCaculation;
}
