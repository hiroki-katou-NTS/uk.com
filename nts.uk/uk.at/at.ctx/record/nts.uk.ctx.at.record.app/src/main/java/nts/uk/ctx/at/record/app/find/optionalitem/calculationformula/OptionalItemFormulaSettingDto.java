/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optionalitem.calculationformula;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class OptionalItemFormulaSettingDto.
 */
@Getter
@Setter
public class OptionalItemFormulaSettingDto {

	/** The calculation atr. */
	// 計算区分
	private int calculationAtr;

	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private String formulaSetting;

	/** The item selection. */
	// 計算項目選択
	private String itemSelection; //TODO...
}
