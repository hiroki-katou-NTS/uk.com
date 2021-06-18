package nts.uk.screen.at.app.command.kmk.kmk004.a;

/**
 * 
 * @author sonnlb
 *
 */
public enum WorkTypeMode {
	/** 通常勤務 */
	REGULAR_WORK(0),

	/** フレックス時間勤務 */
	FLEX_TIME_WORK(1),

	/** 変形労働時間勤務 */
	UNUSUAL_WORK(2);

	/** The value. */
	public final int value;

	private WorkTypeMode(int value) {
		this.value = value;
	}
}
