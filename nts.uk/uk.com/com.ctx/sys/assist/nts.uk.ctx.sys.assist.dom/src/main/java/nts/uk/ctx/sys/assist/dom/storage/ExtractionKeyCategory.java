/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

/**
 * @author nam.lh
 *
 */
public enum ExtractionKeyCategory {
	COMPANY_CD(0),
	EMPLOYEE_CD(5),
	YEAR(6),
	YEAR_MONTH(7),
	YEAR_MONTH_DAY(8);
	
	/** The value. */
	public final int value;


	private ExtractionKeyCategory(int value) {
		this.value = value;
	}
}
