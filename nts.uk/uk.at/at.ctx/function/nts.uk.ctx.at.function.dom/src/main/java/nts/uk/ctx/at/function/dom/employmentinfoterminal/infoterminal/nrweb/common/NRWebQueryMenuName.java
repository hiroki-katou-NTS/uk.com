package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

/**
 * @author thanh_nx
 *
 *         NRWeb照会メニュー名
 */
public enum NRWebQueryMenuName {

	MENU(0, "照会メニュー", "0", "nrl1refmenu"),

	SCHEDULE(1, "勤務予定照会", "1", "webapi/nr/process/query/nrl1refsche"),

	DAILY(2, "日別実績照会", "2", "webapi/nr/process/query/nrl1refday"),

	APPLICATION(3, "申請状況照会", "3", "webapi/nr/process/query/nrl1refsinsei"),

	MONTHLY(4, "月別実績照会", "4", "webapi/nr/process/query/nrl1refmonth"),

	MONTH_WAGE(5, "月間勤務時間賃金状況照会", "5", "webapi/nr/process/query/l1refmonmoney"),

	ANNUAL_WAGE(6, "年間勤務時間賃金状況照会", "6", "webapi/nr/process/query/l1refyearmoney");

	public final int key;

	public final String value;

	public final String no;

	public final String link;

	private NRWebQueryMenuName(int key, String value, String no, String link) {
		this.key = key;
		this.value = value;
		this.no = no;
		this.link = link;
	}

	private static final NRWebQueryMenuName[] values = NRWebQueryMenuName.values();

	public static NRWebQueryMenuName valueOf(Integer key) {
		if (key == null) {
			return null;
		}

		for (NRWebQueryMenuName val : NRWebQueryMenuName.values) {
			if (val.key == key) {
				return val;
			}
		}

		return null;
	}

}
