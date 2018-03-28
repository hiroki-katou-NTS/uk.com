package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;

/**
 * 介護休暇付与残数データ
 * @author xuan vinh
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NursingCareLeaveRemainingData extends AggregateRoot{

	//社員ID
	private String sId;
	
	private LeaveType leaveType;
	
	//使用日数
	private int numOfUsedDay;
	
	public static NursingCareLeaveRemainingData getChildCareHDRemaining(String empId, int usedDay){
		return new NursingCareLeaveRemainingData(empId, EnumAdaptor.valueOf(2, LeaveType.class), usedDay);
	}
	
	public static NursingCareLeaveRemainingData getCareHDRemaining(String empId, int usedDay){
		return new NursingCareLeaveRemainingData(empId, EnumAdaptor.valueOf(1, LeaveType.class), usedDay);
	}
}
