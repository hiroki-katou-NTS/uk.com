package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 * 
 *         予約メニュー送信
 */
@Value
public class SendReservationMenu implements DomainValue {

	/**
	 * 予約メニュー
	 */
	private final String bentoMenu;

	/**
	 * 単位
	 */
	private final String unit;

	/**
	 * 枠番
	 */
	private final Integer frameNumber;

	public SendReservationMenu(String bentoMenu, String unit, Integer frameNumber) {
		super();
		this.bentoMenu = bentoMenu;
		this.unit = unit;
		this.frameNumber = frameNumber;
	}

}
