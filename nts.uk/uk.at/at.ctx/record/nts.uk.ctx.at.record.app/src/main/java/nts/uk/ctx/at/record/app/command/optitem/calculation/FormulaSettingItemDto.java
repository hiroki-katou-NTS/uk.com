/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.InputValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingMethod;

/**
 * The Class FormulaSettingItemDto.
 */
@Getter
@Setter
public class FormulaSettingItemDto implements FormulaSettingItemGetMemento {

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
	 * Gets the setting method.
	 *
	 * @return the setting method
	 */
	@Override
	public SettingMethod getSettingMethod() {
		return EnumAdaptor.valueOf(this.settingMethod, SettingMethod.class);
	}

	/**
	 * Gets the setting item order.
	 *
	 * @return the setting item order
	 */
	@Override
	public SettingItemOrder getSettingItemOrder() {
		return EnumAdaptor.valueOf(this.dispOrder, SettingItemOrder.class);
	}

	/**
	 * Gets the input value.
	 *
	 * @return the input value
	 */
	@Override
	public Optional<InputValue> getInputValue() {
		return Optional.of(new InputValue(this.inputValue));
	}

	/**
	 * Gets the formula id.
	 *
	 * @return the formula id
	 */
	@Override
	public Optional<FormulaId> getFormulaId() {
		return Optional.of(new FormulaId(this.formulaItemId));
	}

}
