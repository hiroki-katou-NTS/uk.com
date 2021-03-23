/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;

/**
 * The Class JpaFlexCalcSettingGetMemento.
 */
public class JpaFlexCalcSettingGetMemento implements FlexCalcSettingGetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;

	/**
	 * Instantiates a new jpa flex calc setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexCalcSettingGetMemento(KshmtWtFle entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento#getRemoveFromWorkTime()
	 */
	@Override
	public UseAtr getRemoveFromWorkTime() {
		return UseAtr.valueOf(this.entity.getDeductFromWorkTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingGetMemento#getCalculateSharing()
	 */
	@Override
	public UseAtr getCalculateSharing() {
		return UseAtr.valueOf(this.entity.getEspecialCalc());
	}
	

}
