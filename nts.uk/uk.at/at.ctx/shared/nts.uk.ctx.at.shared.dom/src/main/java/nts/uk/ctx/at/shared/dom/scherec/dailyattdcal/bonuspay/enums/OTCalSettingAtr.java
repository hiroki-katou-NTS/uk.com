/**
 * 10:23:19 AM Jun 6, 2017
 */
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums;

import lombok.AllArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
public enum OTCalSettingAtr {
	
	// 自動計算しない
	NON_CALCULATION(0, "Enum_AutoCalcAtr_NonCalculation"),
	// 自動計算する
	CALCULATION(1, "Enum_AutoCalcAtr_Calculation"),
	// 残業の自動計算の設定に従う
	FOLLOW_OVERTIME_SETTING(2, "Enum_AutoCalcAtr_FollowOTSetting");

	public final int value;

	public final String nameId;
}
