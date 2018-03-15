package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class AnnualLeaveMaxData extends AggregateRoot{

	private String employeeId;
	
	private Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax;
	
	private Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax;
	
}
