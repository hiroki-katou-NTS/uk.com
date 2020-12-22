/**
 * 10:06:27 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum UnitAtr {
	
	ROUNDING_TIME_1MIN(0, "Enum_RoundingTime_1Min"),
	ROUNDING_TIME_5MIN(1, "Enum_RoundingTime_5Min"),
	ROUNDING_TIME_6MIN(2, "Enum_RoundingTime_6Min"),
	ROUNDING_TIME_10MIN(3, "Enum_RoundingTime_10Min"),
	ROUNDING_TIME_15MIN(4, "Enum_RoundingTime_15Min"),
	ROUNDING_TIME_20MIN(5, "Enum_RoundingTime_20Min"),
	ROUNDING_TIME_30MIN(6, "Enum_RoundingTime_30Min"),
	ROUNDING_TIME_60MIN(7, "Enum_RoundingTime_60Min");
	
	public final int value;
	
	public final String nameId;
}
