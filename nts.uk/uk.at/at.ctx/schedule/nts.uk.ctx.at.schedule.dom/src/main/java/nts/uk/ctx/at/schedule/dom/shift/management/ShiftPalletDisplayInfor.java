package nts.uk.ctx.at.schedule.dom.shift.management;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 	シフトパレットの表示情報
 * @author phongtq
 *
 */

public class ShiftPalletDisplayInfor{

	/** 名称 */
	@Getter
	private ShiftPalletName shiftPalletName;
	
	/** 使用区分 */
	@Getter
	private NotUseAtr shiftPalletAtr;
	
	/** 備考 */
	@Getter
	private ShiftRemarks remarks;

	public ShiftPalletDisplayInfor(ShiftPalletName shiftPalletName, NotUseAtr shiftPalletAtr, ShiftRemarks remarks) {
		this.shiftPalletName = shiftPalletName;
		this.shiftPalletAtr = shiftPalletAtr;
		this.remarks = remarks;
	}
}
