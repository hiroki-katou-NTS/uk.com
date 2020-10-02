package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 	シフトパレットの表示情報
 * @author phongtq
 *
 */

public class ShiftPaletteDisplayInfor{

	/** 名称 */
	@Getter
	private ShiftPaletteName shiftPalletName;
	
	/** 使用区分 */
	@Getter
	private NotUseAtr shiftPalletAtr;
	
	/** 備考 */
	@Getter
	private ShiftRemarks remarks;

	public ShiftPaletteDisplayInfor(ShiftPaletteName shiftPalletName, NotUseAtr shiftPalletAtr, ShiftRemarks remarks) {
		this.shiftPalletName = shiftPalletName;
		this.shiftPalletAtr = shiftPalletAtr;
		this.remarks = remarks;
	}
}
