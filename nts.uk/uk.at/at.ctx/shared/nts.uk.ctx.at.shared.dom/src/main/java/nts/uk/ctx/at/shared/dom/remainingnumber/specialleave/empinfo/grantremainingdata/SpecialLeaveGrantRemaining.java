package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

/**
 * 特休付与残数
 * @author shuichu_ishida
 */
@Getter
public class SpecialLeaveGrantRemaining extends SpecialLeaveGrantRemainingData {

//	/** 特休不足ダミーフラグ */
//	@Setter
//	private boolean dummyAtr = false;
	
	public SpecialLeaveGrantRemaining(LeaveGrantRemaining parent){
		
		this.leaveID = parent.getLeaveID();
		this.cid = parent.getCid();
		this.employeeId = parent.getEmployeeId();
		this.grantDate = parent.getGrantDate();
		this.deadline = parent.getDeadline();
		this.expirationStatus = parent.getExpirationStatus();
		this.registerType = parent.getRegisterType();
		this.details = parent.getDetails();
		
	}
}
