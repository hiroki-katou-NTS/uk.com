package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         予約受信データExport
 */
@Value
public class ReservReceptDataExport {
	/**
	 * ID番号
	 */
	private final String idNumber;

	/**
	 * メニュー
	 */
	private final String menu;

	/**
	 * 年月日
	 */
	private final String ymd;

	/**
	 * 時分秒
	 */
	private final String time;

	/**
	 * 数量
	 */
	private final String quantity;

	public ReservReceptDataExport(String idNumber, String menu, String ymd, String time, String quantity) {
		this.idNumber = idNumber;
		this.menu = menu;
		this.ymd = ymd;
		this.time = time;
		this.quantity = quantity;
	}

}
