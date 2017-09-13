/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CalculationFormulaSetting.
 */
// 任意項目計算式設定
@Getter
public class OptionalItemFormulaSetting extends DomainObject {

	/** The calculation classification. */
	// 計算区分
	private CalculationAtr calculationAtr;

	// ===================== Optional ======================= //
	/** The formula setting. */
	// 計算式設定
	private FormulaSetting formulaSetting;

	/** The calculation item selection. */
	// 計算項目選択
	private ItemSelection itemSelection;

}
