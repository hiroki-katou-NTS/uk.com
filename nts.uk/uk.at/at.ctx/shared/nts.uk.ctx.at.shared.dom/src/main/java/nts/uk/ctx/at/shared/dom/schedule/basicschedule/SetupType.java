package nts.uk.ctx.at.shared.dom.schedule.basicschedule;
/**
 * 
 * @author sonnh1
 *
 */
public enum SetupType {

	// 必須である
	REQUIRED(0),

	// 任意である
	OPTIONAL(1),

	// 不要である
	NOT_REQUIRED(2);

	public int value;

	private SetupType(int value) {
		this.value = value;
	}
}
