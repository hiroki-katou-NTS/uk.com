/**
 * 10:06:42 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum RoundingAtr {

	// 切り捨て
	ROUNDING_DOWN(0, "Enum_Rounding_Down"),
	// 切り上げ
	ROUNDING_UP(1, "Enum_Rounding_Up"),
	// 未満切捨、以上切上
	ROUNDING_LESSTHANOREQUAL(2, "Enum_Rounding_LessThanOrEqual");

	public final int value;

	public final String nameId;
}
