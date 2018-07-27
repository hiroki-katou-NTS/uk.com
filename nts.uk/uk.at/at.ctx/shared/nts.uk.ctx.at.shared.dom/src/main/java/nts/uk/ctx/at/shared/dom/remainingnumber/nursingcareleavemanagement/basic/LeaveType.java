package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LeaveType {
	LEAVE_FOR_CARE(1),
	CHILD_CARE_LEAVE(2);
	
	public final int value;
}
