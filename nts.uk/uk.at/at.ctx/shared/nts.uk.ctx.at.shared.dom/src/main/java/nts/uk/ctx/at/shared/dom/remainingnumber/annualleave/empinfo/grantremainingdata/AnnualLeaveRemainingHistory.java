package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT - 年休付与残数履歴データ
 *
 */
@Getter
public class AnnualLeaveRemainingHistory extends AnnualLeaveGrantRemainingData{
	
	// 年月
	private YearMonth yearMonth;

	// 締めID
	private ClosureId closureId;

	// 締め日
	private ClosureDate closureDate;

	public AnnualLeaveRemainingHistory(AnnualLeaveGrantRemainingData data, YearMonth yearMonth, ClosureId clousureId, ClosureDate closureDate) {
		this.leaveID = data.getLeaveID();
		this.employeeId = data.getEmployeeId();
		this.yearMonth = yearMonth;
		this.closureId = clousureId;
		this.closureDate = closureDate;
		this.grantDate = data.getGrantDate();
		this.deadline = data.getDeadline();
		this.expirationStatus = data.getExpirationStatus();
		this.registerType = data.getRegisterType();
		this.details = (AnnualLeaveNumberInfo) data.getDetails();
		this.annualLeaveConditionInfo = data.getAnnualLeaveConditionInfo();
	}

}
