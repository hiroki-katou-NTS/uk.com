package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

@Value
public class SendReservationMenuExport implements SendNRInfoDataExport {
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

	public SendReservationMenuExport(String bentoMenu, String unit, Integer frameNumber) {
		super();
		this.bentoMenu = bentoMenu;
		this.unit = unit;
		this.frameNumber = frameNumber;
	}
}
