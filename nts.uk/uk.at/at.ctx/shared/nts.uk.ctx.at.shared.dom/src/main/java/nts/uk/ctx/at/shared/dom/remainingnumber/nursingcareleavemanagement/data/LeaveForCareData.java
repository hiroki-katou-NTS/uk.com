package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.NumOfUseDay;

/**
 * 介護休暇付与残数データ
 * @author xuan vinh
 *
 */

public class LeaveForCareData extends NursingCareLeaveRemainingData{
	
	public LeaveForCareData() {
	}
	
	public LeaveForCareData(String sId, NumOfUseDay numOfUsedDay) {
		super(sId, LeaveType.LEAVE_FOR_CARE, numOfUsedDay);
	}
	
	public static LeaveForCareData getCareHDRemaining(String empId, Double usedDay) {
		return new LeaveForCareData(empId, new NumOfUseDay(usedDay));
	}

}
