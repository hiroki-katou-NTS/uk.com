package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor
public enum AssignmentMethod {

	HOLIDAY(0), // 休日

	SHIFT(1), // シフト

	TIME_ZONE(2); // 時間帯

	public final int value;
	
	public static AssignmentMethod of(int value) {
		return EnumAdaptor.valueOf(value, AssignmentMethod.class);
	}
}
