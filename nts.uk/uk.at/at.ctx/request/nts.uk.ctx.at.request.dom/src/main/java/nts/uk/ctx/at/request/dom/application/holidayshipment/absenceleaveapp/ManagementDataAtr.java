package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

/**
 * 管理データ区分
 * 
 * @author sonnlb
 */

public enum ManagementDataAtr {

	/**
	 * 確定
	 */
	CONFIRM(0),
	/**
	 * 暫定
	 */
	INTERIM(1);

	public final int value;

	private ManagementDataAtr(int value) {
		this.value = value;
	}
}
