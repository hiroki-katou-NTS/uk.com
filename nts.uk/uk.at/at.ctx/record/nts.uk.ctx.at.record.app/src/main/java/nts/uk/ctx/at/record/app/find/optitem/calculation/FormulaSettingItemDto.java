/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingItemSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.InputValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingMethod;

/**
 * The Class FormulaSettingItemDto.
 */
@Getter
@Setter
public class FormulaSettingItemDto implements FormulaSettingItemSetMemento {

	/** The setting method. */
	private int settingMethod;

	/** The disp order. */
	private int dispOrder;

	// ===================== Optional ======================= //
	/** The input value. */
	private BigDecimal inputValue;

	/** The formula item id. */
	private String formulaItemId;

	/**
	 * Sets the setting method.
	 *
	 * @param method the new setting method
	 */
	@Override
	public void setSettingMethod(SettingMethod method) {
		this.settingMethod = method.value;
	}

	/**
	 * Sets the setting item order.
	 *
	 * @param order the new setting item order
	 */
	@Override
	public void setSettingItemOrder(SettingItemOrder order) {
		this.dispOrder = order.value;
	}

	/**
	 * Sets the input value.
	 *
	 * @param value the new input value
	 */
	@Override
	public void setInputValue(Optional<InputValue> value) {
		if(value.isPresent()) {
			this.inputValue = value.get().v();
		}
	}

	/**
	 * Sets the formula id.
	 *
	 * @param id
	 *            the new formula id
	 */
	@Override
	public void setFormulaId(Optional<FormulaId> id) {
		if(id.isPresent()) {
			this.formulaItemId = id.get().v();
		}
	}

}
