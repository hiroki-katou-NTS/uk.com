package nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule;

import java.util.Optional;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Value
public class FakeSupportTicket {
	
	private String employeeId;
	
	private TargetOrgIdenInfor supportDestination;
	
	private FakeSupportType supportType;
	
	private GeneralDate date;
	
	private Optional<TimeSpanForCalc> timeSpan;
	
	public boolean isSame(FakeSupportTicket otherTicket) {
		
		return this.employeeId.equals(otherTicket.getEmployeeId())
				&& this.supportDestination.getTargetId().equals(otherTicket.getSupportDestination().getTargetId())
				&& this.supportType == otherTicket.getSupportType()
				&& this.date.equals(otherTicket)
				&& this.timeSpan.equals(otherTicket.getTimeSpan());
	}

}
