package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * @author thanh_nx
 *
 *         事前申請により控除する時間
 */
@AllArgsConstructor
@Data
public class TimeDeductByPriorAppOutput {

	//残業枠NO
	private OverTimeFrameNo overTimeNo;
	
	//控除する時間
	private AttendanceTime timeDeduct;
}
