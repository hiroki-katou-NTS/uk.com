package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemaining;

/**
 * 特休付与残数
 * @author shuichu_ishida
 */
@Getter
public class SpecialLeaveGrantRemaining extends SpecialLeaveGrantRemainingData {

	/** 特休不足ダミーフラグ */
	@Setter
	private boolean dummyAtr = false;

	public SpecialLeaveGrantRemaining(LeaveGrantRemaining parent){

		this.leaveID = parent.getLeaveID();
		this.employeeId = parent.getEmployeeId();
		this.grantDate = parent.getGrantDate();
		this.deadline = parent.getDeadline();
		this.expirationStatus = parent.getExpirationStatus();
		this.registerType = parent.getRegisterType();
		this.details = parent.getDetails();
		this.dummyAtr = parent.isDummyAtr();

	}


	private SpecialLeaveGrantRemaining() {}


	public static SpecialLeaveGrantRemaining of(SpecialLeaveGrantRemainingData data, boolean dummyAtr) {

		SpecialLeaveGrantRemaining domain = new SpecialLeaveGrantRemaining();

		domain.leaveID = data.getLeaveID();
		domain.employeeId = data.getEmployeeId();
		domain.grantDate = data.getGrantDate();
		domain.deadline = data.getDeadline();
		domain.expirationStatus = data.getExpirationStatus();
		domain.registerType = data.getRegisterType();
		domain.details = data.getDetails();
		domain.dummyAtr = dummyAtr;

		return domain;
	}
}
