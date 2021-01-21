package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

public enum StatusOfWorkAvailabilityAndSchedule {

	NO_EXPECTATION(0),	// 希望がない
	
	NOT_MATCHING(1), // 一致していない
	
	MATCHING(2); // 一致している

	public int value;
	
	private StatusOfWorkAvailabilityAndSchedule(int value) {
		this.value = value;
	}
}


