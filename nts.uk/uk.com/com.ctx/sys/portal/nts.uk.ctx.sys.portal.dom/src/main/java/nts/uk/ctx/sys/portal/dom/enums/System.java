package nts.uk.ctx.sys.portal.dom.enums;

/**
 * 
 * @author sonnh1
 *
 */
public enum System {
	/**
	 * 0:人事郎
	 */
	JINJIROU(0),
	/**
	 * 1:勤次郎
	 */
	TIME_SHEET(1),
	/**
	 * 2:オフィスヘルパー
	 */
	OFFICE_HELPER(2),
	/**
	 * 3:Ｑ太郎
	 */
	KYUYOU(3);

	public final int value;

	System(int type) {
		this.value = type;
	}
}
