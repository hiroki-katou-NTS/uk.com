package nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit;
/**
 * 
 * @author hieult
 *
 */
/** 周期の区分 */
public enum SegmentationOfCycle {

	/** 実行日を含む周期の前周期 */
	ThePreviousCycle(0, "実行日を含む周期の前周期"),

	/** 実行日を含む周期 */
	Period(1, "実行日を含む周期"),

	/** 実行日を含む周期の翌周期 */
	TheNextCycle(2, "実行日を含む周期の翌周期");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SegmentationOfCycle(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
