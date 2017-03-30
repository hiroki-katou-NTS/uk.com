package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;

/**
 * 
 * @author sonnh
 *
 */
public enum DisplaySet {
	/**
	 * 0 - 表示しない
	 */
	NOT_DISPLAY(0),
	/**
	 * 1- 表示する
	 */
	DISPLAY(1);

	public final int value;

	DisplaySet(int value) {
		this.value = value;
	}
}
