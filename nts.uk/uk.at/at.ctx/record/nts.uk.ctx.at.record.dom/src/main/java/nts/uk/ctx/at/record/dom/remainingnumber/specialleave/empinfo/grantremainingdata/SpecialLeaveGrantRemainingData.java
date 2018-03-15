package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Getter
public class SpecialLeaveGrantRemainingData extends AggregateRoot{
	
	private String employeeId;
	
	private String specialLeaveCode;
	
	private GeneralDate grantData;
	
	private GeneralDate deadline;
	
	private LeaveExpirationStatus expirationStatus;
	
	private GrantRemainRegisterType registerType;
	
	private SpecialLeaveNumberInfo details;

}
