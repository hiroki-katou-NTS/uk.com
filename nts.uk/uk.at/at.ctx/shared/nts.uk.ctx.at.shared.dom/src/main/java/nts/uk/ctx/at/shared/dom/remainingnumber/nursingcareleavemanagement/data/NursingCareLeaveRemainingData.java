package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.NumOfUseDay;

/**
 * 子の看護・介護休暇付与残数データ
 * 
 * @author xuan vinh
 *
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class NursingCareLeaveRemainingData extends AggregateRoot {

	// 社員ID
	private String sId;

	private LeaveType leaveType;

	// 使用日数
	private NumOfUseDay numOfUsedDay;
	
	public NursingCareLeaveRemainingData(String sId, LeaveType leaveType, NumOfUseDay numOfUsedDay) {
		super();
		this.sId = sId;
		this.leaveType = leaveType;
		this.numOfUsedDay = numOfUsedDay;
	}
	
}
