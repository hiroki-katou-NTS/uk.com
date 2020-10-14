package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

public enum StatusOfWorkExpectationAndSchedule {

	NO_EXPECTATION(0),	// 希望がない
	
	NOT_MATCHING(1), // 一致していない
	
	MATCHING(2); // 一致している

	public int value;
	
	private StatusOfWorkExpectationAndSchedule(int value) {
		this.value = value;
	}
}


