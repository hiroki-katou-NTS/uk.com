package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

/**
 * 
 * @author sonnh1
 *
 */
public enum WorkStyle {
	
	ONE_DAY_REST(0),

	MORNING_WORK(1),

	AFTERNOON_WORK(2),

	ONE_DAY_WORK(4);

	public int value;

	private WorkStyle(int value) {
		this.value = value;
	}
}
