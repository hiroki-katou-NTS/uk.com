package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	private String sId;
	private LeaveType leaveType;
	private int numOfUsedDay;
}
