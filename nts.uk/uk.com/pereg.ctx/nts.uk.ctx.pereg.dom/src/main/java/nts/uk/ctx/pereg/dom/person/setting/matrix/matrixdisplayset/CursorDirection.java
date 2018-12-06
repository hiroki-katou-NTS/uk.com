/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset;

/**
 * @author hieult
 *
 */
public enum CursorDirection {

	/** 0: 縦 */
	VERTICAL(0),

	/** 1: 横 */
	HORIZONTAL(1);

	public int value;

	private CursorDirection(int type) {
		this.value = type;
	}

}
