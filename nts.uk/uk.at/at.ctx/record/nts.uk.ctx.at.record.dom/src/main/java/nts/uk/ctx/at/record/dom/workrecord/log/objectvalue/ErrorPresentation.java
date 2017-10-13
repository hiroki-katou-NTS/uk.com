/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.log.objectvalue;

/**
 * @author danpv
 *
 */
public enum ErrorPresentation {

	// エラーあり
	HasError(0),

	// エラーなし
	NoError(1);

	public final int value;

	private ErrorPresentation(int value) {
		this.value = value;
	}

}
