package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

public enum AssignmentMethod {

	HOLIDAY(0), // 休日

	SHIFT(1), // シフト

	TIME_ZONE(2); // 時間帯

	public int value;

	private AssignmentMethod(int value) {
		this.value = value;
	}

}
