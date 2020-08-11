package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.empinfo.grantremainingdata.HolidayOver60hGrantRemainingData;

/**
 * 60H超休付与残数   
 * @author masaaki_jinno
 *
 */
public class HolidayOver60hGrantRemaining extends HolidayOver60hGrantRemainingData{

	/** 60H超休不足ダミーフラグ */
	@Setter
	private boolean dummyAtr = false;
	
	public HolidayOver60hGrantRemaining(HolidayOver60hGrantRemainingData parent){
		
		this.annLeavID = parent.getAnnLeavID();
		this.cid = parent.getCid();
		this.employeeId = parent.getEmployeeId();
		this.grantDate = parent.getGrantDate();
		this.deadline = parent.getDeadline();
		this.expirationStatus = parent.getExpirationStatus();
		this.registerType = parent.getRegisterType();
		this.details = parent.getDetails();
		
		//this.annualLeaveConditionInfo = parent.getAnnualLeaveConditionInfo();
		
		this.dummyAtr = false;
	}
	
}

