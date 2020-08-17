package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 特休付与残数
 * @author shuichu_ishida
 */
@Getter
public class SpecialLeaveGrantRemaining extends SpecialLeaveGrantRemainingData {

	/** 特休不足ダミーフラグ */
	@Setter
	private boolean dummyAtr = false;
	
	public SpecialLeaveGrantRemaining(AnnualLeaveGrantRemainingData parent){
		
		this.annLeavID = parent.getAnnLeavID();
		this.cid = parent.getCid();
		this.employeeId = parent.getEmployeeId();
		this.grantDate = parent.getGrantDate();
		this.deadline = parent.getDeadline();
		this.expirationStatus = parent.getExpirationStatus();
		this.registerType = parent.getRegisterType();
		this.details = parent.getDetails();
		
		// this.annualLeaveConditionInfo = parent.getAnnualLeaveConditionInfo();
		
		this.dummyAtr = false;
	}
}
