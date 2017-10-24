/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ErrorPresent {

	//0 : エラーあり
	HAS_ERROR(0),

	//1 : エラーなし
	NO_ERROR(1);

	public final int value;

	private ErrorPresent(int value) {
		this.value = value;
	}

}
