/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingItemGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.InputValue;
import nts.uk.ctx.at.record.dom.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.SettingMethod;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSettingPK;

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
	public InputValue getInputValue() {
		if (SettingItemOrder.LEFT.value == this.settingItemOrder) {
			return new InputValue(this.setting.getLeftInputVal());
		}
		 return new InputValue(this.setting.getRightInputVal());
	}

	/**
	 * Gets the formula id.
	 *
	 * @return the formula id
	 */
	@Override
	public FormulaId getFormulaId() {
		if (SettingItemOrder.LEFT.value == this.settingItemOrder) {
			return new FormulaId(this.setting.getLeftFormulaItemId());
		}
		return new FormulaId(this.setting.getRightFormulaItemId());
	}

}
