package nts.uk.ctx.at.shared.dom.yearholidaygrant;

/**
 * 付与基準日
	0:入社日
	1:年休付与基準日
 * @author TanLV
 *
 */
public enum GrantReferenceDate {
	/** 入社日 */
	HIRE_DATE(0),
	/** 年休付与基準日 */
	YEAR_HD_REFERENCE_DATE(1);
	
	public final int value;
	
	GrantReferenceDate(int value) {
		this.value = value;
	}
}
