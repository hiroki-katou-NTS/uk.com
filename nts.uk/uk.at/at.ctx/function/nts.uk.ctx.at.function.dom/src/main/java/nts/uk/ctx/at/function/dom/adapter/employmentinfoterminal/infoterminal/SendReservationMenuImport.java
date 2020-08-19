package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 */
@Value
public class SendReservationMenuImport {
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

	public SendReservationMenuImport(String bentoMenu, String unit, Integer frameNumber) {
		super();
		this.bentoMenu = bentoMenu;
		this.unit = unit;
		this.frameNumber = frameNumber;
	}
}
