package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalculateMethod {
	
	MAKE_ATTENDANCE_DAY(0),
	
	DO_NOT_GO_TO_WORK(1),
	
	EXCLUDE_FROM_WORK_DAY(2),
	
	TIME_DIGEST_VACATION(3);
	
	public final int value;
}
