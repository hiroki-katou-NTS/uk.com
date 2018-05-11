package nts.uk.ctx.at.request.dom.application.common.adapter.frame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeInputCaculation {
	/**
	 * 勤怠種類
	 */
	private int attendanceID;
	/**
	 * 勤怠項目NO
	 */
	private int frameNo;
	/**時間*/
	private Integer resultCaculation;
}
