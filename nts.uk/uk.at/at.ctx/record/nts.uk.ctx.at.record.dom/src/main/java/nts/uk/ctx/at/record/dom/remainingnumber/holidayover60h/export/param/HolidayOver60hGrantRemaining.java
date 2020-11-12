package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.empinfo.grantremainingdata.HolidayOver60hGrantRemainingData;

/**
 * 60H超休付与残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class HolidayOver60hGrantRemaining extends HolidayOver60hGrantRemainingData{

	public HolidayOver60hGrantRemaining(HolidayOver60hGrantRemainingData parent){

		this.leaveID = parent.getLeaveID();
		this.cid = parent.getCid();
		this.employeeId = parent.getEmployeeId();
		this.grantDate = parent.getGrantDate();
		this.deadline = parent.getDeadline();
		this.expirationStatus = parent.getExpirationStatus();
		this.registerType = parent.getRegisterType();
		this.details = parent.getDetails();
		this.dummyAtr = parent.isDummyAtr();
	}
}

