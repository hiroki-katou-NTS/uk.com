/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.Optional;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSettingPK;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingItemGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.InputValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingMethod;

/**
 * The Class JpaFormulaSettingItemGetMemento.
 */
public class JpaFormulaSettingItemGetMemento implements FormulaSettingItemGetMemento{

	
	/** The setting. */
	private KrcmtFormulaSetting setting;
	
	/** The setting item order. */
	private int settingItemOrder;
	
	/**
	 * Instantiates a new jpa formula setting item get memento.
	 *
	 * @param setting the setting
	 * @param settingItemOrder the setting item order
	 */
	public JpaFormulaSettingItemGetMemento(KrcmtFormulaSetting setting,int settingItemOrder) {
		if(setting.getKrcmtFormulaSettingPK() == null){
			setting.setKrcmtFormulaSettingPK(new KrcmtFormulaSettingPK());
		}
		this.settingItemOrder = settingItemOrder;
		this.setting = setting;
	}
	
	/**
	 * Gets the setting method.
	 *
	 * @return the setting method
	 */
	@Override
	public SettingMethod getSettingMethod() {
		if (SettingItemOrder.LEFT.value == this.settingItemOrder) {
			return SettingMethod.valueOf(this.setting.getLeftSetMethod());
		}
		return SettingMethod.valueOf(this.setting.getRightSetMethod());
	}

	/**
	 * Gets the setting item order.
	 *
	 * @return the setting item order
	 */
	@Override
	public SettingItemOrder getSettingItemOrder() {
		return SettingItemOrder.valueOf(this.settingItemOrder);
	}

	/**
	 * Gets the input value.
	 *
	 * @return the input value
	 */
	@Override
	public Optional<InputValue> getInputValue() {
		// Check item's side
		if (SettingItemOrder.LEFT.value == this.settingItemOrder) {
			return Optional.of(new InputValue(this.setting.getLeftInputVal()));
		}
		
		return Optional.of(new InputValue(this.setting.getRightInputVal()));
	}

	/**
	 * Gets the formula id.
	 *
	 * @return the formula id
	 */
	@Override
	public Optional<FormulaId> getFormulaId() {
		// Check item's side
		if (SettingItemOrder.LEFT.value == this.settingItemOrder) {
			return Optional.of(new FormulaId(this.setting.getLeftFormulaItemId()));
		}
		
		return Optional.of(new FormulaId(this.setting.getRightFormulaItemId()));
	}

}
