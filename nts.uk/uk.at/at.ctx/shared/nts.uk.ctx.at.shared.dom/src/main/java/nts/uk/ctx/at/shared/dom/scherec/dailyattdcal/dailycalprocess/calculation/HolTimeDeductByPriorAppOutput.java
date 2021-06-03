package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;

/**
 * @author thanh_nx
 *
 *         事前申請により控除する時間
 */
@AllArgsConstructor
@Data
public class HolTimeDeductByPriorAppOutput {

	//休出枠NO
	private HolidayWorkFrameNo holidayTimeNo;
	
	//控除する時間
	private AttendanceTime timeDeduct;
}
