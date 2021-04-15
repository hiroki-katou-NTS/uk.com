package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT - 年休付与時点残数履歴データ
 *
 */
@Getter
public class AnnualLeaveTimeRemainingHistory extends AnnualLeaveGrantRemainingData {

	/** 付与処理日 */
	private GeneralDate grantProcessDate;

	public AnnualLeaveTimeRemainingHistory(AnnualLeaveGrantRemainingData data, GeneralDate grantProcessDate) {
		this.leaveID = data.getLeaveID();
		this.employeeId = data.getEmployeeId();
		this.grantProcessDate = grantProcessDate;
		this.grantDate = data.getGrantDate();
		this.deadline = data.getDeadline();
		this.expirationStatus = data.getExpirationStatus();
		this.registerType = data.getRegisterType();
		this.details = (AnnualLeaveNumberInfo) data.getDetails();
		this.annualLeaveConditionInfo = data.getAnnualLeaveConditionInfo();
	}

}
