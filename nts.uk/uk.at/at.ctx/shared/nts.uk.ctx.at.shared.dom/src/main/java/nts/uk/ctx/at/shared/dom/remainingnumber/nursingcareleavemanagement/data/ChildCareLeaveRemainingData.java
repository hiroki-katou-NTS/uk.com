package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.NumOfUseDay;

/**
 * 子の看護休暇付与残数データ
 * 
 * @author danpv
 *
 */
public class ChildCareLeaveRemainingData extends NursingCareLeaveRemainingData {
	
	public ChildCareLeaveRemainingData() {
		
	}

	public ChildCareLeaveRemainingData(String sId, NumOfUseDay numOfUsedDay) {
		super(sId, LeaveType.CHILD_CARE_LEAVE, numOfUsedDay);
	}

	public static ChildCareLeaveRemainingData getChildCareHDRemaining(String empId, Double usedDay) {
		return new ChildCareLeaveRemainingData(empId, new NumOfUseDay(usedDay));
	}

}
