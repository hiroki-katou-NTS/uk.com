package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Getter
public class AnnualLeaveGrantRemainingData extends AggregateRoot{
	
	private String employeeId;
	
	private GeneralDate grantDate;
	
	private GeneralDate deadline;
	
	private LeaveExpirationStatus expirationStatus;
	
	private GrantRemainRegisterType registerType;
	
	private AnnualLeaveNumberInfo details;
	
	private Optional<AnnualLeaveConditionInfo> annualLeaveConditionInfo;

}
