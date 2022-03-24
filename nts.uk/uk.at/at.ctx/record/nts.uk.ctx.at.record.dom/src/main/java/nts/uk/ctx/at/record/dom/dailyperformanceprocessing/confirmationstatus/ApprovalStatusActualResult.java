package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalStatusActualResult extends ConfirmStatusActualResult{
	
	/**
	 * 承認状況
	 */
	@Setter
	private boolean statusNormal;

	public ApprovalStatusActualResult(String employeeId, GeneralDate date, boolean status, boolean statusNormal) {
		super(employeeId, date, status);
		this.statusNormal = statusNormal;
	}
	
	public ApprovalStatusActualResult updateApprovalStatus(boolean status) {
		this.setStatus(status);
		return this;
	}
	
	public boolean notDisableNormal() {
		if(statusNormal) return this.permissionRelease == ReleasedAtr.CAN_IMPLEMENT;
		return permissionCheck == ReleasedAtr.CAN_IMPLEMENT;
	}

	@Override
	public boolean equals(Object other) {
          return super.equals(other) && ((ApprovalStatusActualResult)other).statusNormal == this.statusNormal;
	}
	
	
}
