package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
//domain name: 年休上限データ
public class AnnualLeaveMaxData extends AggregateRoot{

	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 半日年休上限
	 */
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;
	
	/**
	 * 時間年休上限
	 */
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;
	
}
