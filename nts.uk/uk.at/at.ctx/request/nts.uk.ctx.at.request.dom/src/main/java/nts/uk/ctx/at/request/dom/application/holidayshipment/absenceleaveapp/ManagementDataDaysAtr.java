package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

/**
 * 管理データ日数単位
 * 
 * @author sonnlb
 */
public enum ManagementDataDaysAtr {
	/**
	 * 1.0日
	 */

	FULL_DAY(0),
	/**
	 * 0.5日
	 */
	HALF_DAY(1);

	public final int value;

	private ManagementDataDaysAtr(int value) {
		this.value = value;
	}
}
