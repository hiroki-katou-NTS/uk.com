/**
 * 10:24:00 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum HTCalSettingAtr {

	// 自動計算しない
	NON_CALCULATION(0, "Enum_AutoCalcAtr_NonCalculation"),
	// 自動計算する
	CALCULATION(1, "Enum_AutoCalcAtr_Calculation"),
	// 休出の自動計算の設定に従う
	FOLLOW_HOLIDAY_SETTING(2, "Enum_AutoCalcAtr_FollowHolidaySetting");

	public final int value;

	public final String nameId;
}
