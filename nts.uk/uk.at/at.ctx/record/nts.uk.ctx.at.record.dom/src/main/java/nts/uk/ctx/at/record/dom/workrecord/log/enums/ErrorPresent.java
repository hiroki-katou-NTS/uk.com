/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.enums;

/**
 * @author danpv
 *
 */
public enum ErrorPresent {

	// エラーあり
	HasError(0),

	// エラーなし
	NoError(1);

	public final int value;

	private ErrorPresent(int value) {
		this.value = value;
	}

}
