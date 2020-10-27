/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flexset;

import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexCalcSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFle;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFlePK;

/**
 * The Class JpaFlexCalcSettingSetMemento.
 */
public class JpaFlexCalcSettingSetMemento implements FlexCalcSettingSetMemento {
	
	/** The entity. */
	private KshmtWtFle entity;

	/**
	 * Instantiates a new jpa flex calc setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexCalcSettingSetMemento(KshmtWtFle entity) {
		super();
		if(entity.getKshmtWtFlePK() == null){
			entity.setKshmtWtFlePK(new KshmtWtFlePK());
		}
		this.entity = entity;
	}

	/**
	 * Sets the removes the from work time.
	 *
	 * @param removeFromWorkTime the new removes the from work time
	 */
	@Override
	public void setRemoveFromWorkTime(UseAtr removeFromWorkTime) {
		this.entity.setDeductFromWorkTime(removeFromWorkTime.value);
		
	}

	/**
	 * Sets the calculate sharing.
	 *
	 * @param calculateSharing the new calculate sharing
	 */
	@Override
	public void setCalculateSharing(UseAtr calculateSharing) {
		this.entity.setEspecialCalc(calculateSharing.value);
	}
	

}
