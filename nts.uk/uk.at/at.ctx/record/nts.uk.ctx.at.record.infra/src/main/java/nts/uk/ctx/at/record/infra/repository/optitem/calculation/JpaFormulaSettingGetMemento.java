/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSettingPK;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSettingItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.OperatorAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingItemOrder;

/**
 * The Class JpaFormulaSettingGetMemento.
 */
public class JpaFormulaSettingGetMemento implements FormulaSettingGetMemento{
	
	/** The setting. */
	private KrcmtFormulaSetting setting;
	
	
	/**
	 * Instantiates a new jpa formula setting get memento.
	 *
	 * @param setting the setting
	 * @param operatorAtr the operator atr
	 */
	public JpaFormulaSettingGetMemento(KrcmtFormulaSetting setting) {
		if (setting.getKrcmtFormulaSettingPK() == null) {
			setting.setKrcmtFormulaSettingPK(new KrcmtFormulaSettingPK());
		}
		this.setting = setting;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#getMinusSegment()
	 */
	@Override
	public MinusSegment getMinusSegment() {
		return MinusSegment.valueOf(this.setting.getMinusSegment()) ;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#getOperatorAtr()
	 */
	@Override
	public OperatorAtr getOperatorAtr() {
		return OperatorAtr.valueOf(this.setting.getOperator());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#getLeftItem()
	 */
	@Override
	public FormulaSettingItem getLeftItem() {
		return new FormulaSettingItem(
				new JpaFormulaSettingItemGetMemento(this.setting, SettingItemOrder.LEFT.value));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaSettingGetMemento#getRightItem()
	 */
	@Override
	public FormulaSettingItem getRightItem() {
		return new FormulaSettingItem(
				new JpaFormulaSettingItemGetMemento(this.setting, SettingItemOrder.RIGHT.value));
	}

}
