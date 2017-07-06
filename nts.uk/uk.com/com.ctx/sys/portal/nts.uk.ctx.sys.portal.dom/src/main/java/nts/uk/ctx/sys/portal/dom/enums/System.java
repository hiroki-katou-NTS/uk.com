package nts.uk.ctx.sys.portal.dom.enums;

/**
 * 
 * @author sonnh1
 *
 */
public enum System {
	/**
	 * 0:共通
	 */
	Common(0),
	/**
	 * 1:勤次郎
	 */
	TimeSheet(1),
	/**
	 * 2:オフィスヘルパー
	 */
	OfficeHelper(2),
	/**
	 * 3:Ｑ太郎
	 */
	Kyuyou(3),
	/**
	 * 4:人事郎
	 */
	Jinjirou(4);

	public final int value;

	System(int type) {
		this.value = type;
	}
}
