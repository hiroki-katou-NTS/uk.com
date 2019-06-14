package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

/**
 * 
 * @author thanh.tq 年間所定労働日数区分
 *
 */
public enum ClassifiedWorkingDaysPerYear {
	NOT_COVERED(0, "対象外"), COVERED(1, "対象");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ClassifiedWorkingDaysPerYear(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
