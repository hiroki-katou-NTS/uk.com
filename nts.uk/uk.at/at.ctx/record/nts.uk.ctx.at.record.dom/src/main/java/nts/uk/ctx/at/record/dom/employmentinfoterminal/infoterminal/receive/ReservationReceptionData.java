package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 * 
 *         予約受信データ
 */
@Value
public class ReservationReceptionData implements ReceptionData {

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

	public ReservationReceptionData(String idNumber, String menu, String ymd, String time, String quantity) {
		super();
		this.idNumber = idNumber;
		this.menu = menu;
		this.ymd = ymd;
		this.time = time;
		this.quantity = quantity;
	}

	@Override
	public GeneralDateTime getDateTime() {
		int yy = GeneralDate.today().year() / 100;
		int ymdTemp = Integer.parseInt(String.valueOf(yy) + ymd);
		
		int timeNumber = Integer.parseInt(time);
		
		return GeneralDateTime.ymdhms(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100,
				timeNumber / 10000, (timeNumber - (timeNumber / 10000) * 10000) / 100, timeNumber % 100);
	}

	public Integer getBentoFrame() {
		return NRHelper.BENTO_NO.indexOf(menu.trim()) + 1;
	}
}
