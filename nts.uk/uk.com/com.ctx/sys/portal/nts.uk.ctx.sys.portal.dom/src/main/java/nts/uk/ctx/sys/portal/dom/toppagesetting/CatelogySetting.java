package nts.uk.ctx.sys.portal.dom.toppagesetting;

/**
 * 
 * @author sonnh1
 *
 */
public enum CatelogySetting {
	/**
	 * 0 - 分けない
	 */
	NOT_DIVIDE(0),
	/**
	 * 1 - 分ける
	 */
	DIVIDE(1);

	public final int value;

	CatelogySetting(int type) {
		this.value = type;
	}
}
