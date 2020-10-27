/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFl;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlPK;

/**
 * The Class JpaFlexFlowRestSetSetMemento.
 */
public class JpaFlexFlowRestSetSetMemento implements FlowRestSetSetMemento {

	/** The entity. */
	private KshmtWtFleBrFl entity;
	
	/**
	 * Instantiates a new jpa flex flow rest set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFlowRestSetSetMemento(KshmtWtFleBrFl entity) {
		super();
		if(entity.getKshmtWtFleBrFlPK() == null){
			entity.setKshmtWtFleBrFlPK(new KshmtWtFleBrFlPK());
		}
		this.entity = entity;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setUseStamp(boolean)
	 */
	@Override
	public void setUseStamp(boolean val) {
		this.entity.setUseStamp(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setUseStampCalcMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestClockCalcMethod)
	 */
	@Override
	public void setUseStampCalcMethod(FlowRestClockCalcMethod method) {
		this.entity.setUseStampCalcMethod(method.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setTimeManagerSetAtr(nts.uk.ctx.at.shared.dom.worktime.common.
	 * RestClockManageAtr)
	 */
	@Override
	public void setTimeManagerSetAtr(RestClockManageAtr atr) {
		this.entity.setTimeManagerSetAtr(atr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowRestCalcMethod)
	 */
	@Override
	public void setCalculateMethod(FlowRestCalcMethod method) {
		this.entity.setCalculateMethod(method.value);
	}
	

}
