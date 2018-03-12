/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;

/**
 * The Class JpaFlowFixedRestSetSetMemento.
 */
public class JpaFlowFixedRestSetSetMemento implements FlowFixedRestSetSetMemento {

	/** The entity. */
	KshmtFlowRestSet entity;

	/**
	 * Instantiates a new jpa flow fixed rest set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlowFixedRestSetSetMemento(KshmtFlowRestSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setIsReferRestTime(boolean)
	 */
	@Override
	public void setIsReferRestTime(boolean val) {
		this.entity.setIsReferRestTime(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setUsePrivateGoOutRest(boolean)
	 */
	@Override
	public void setUsePrivateGoOutRest(boolean val) {
		this.entity.setUserPrivateGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setUseAssoGoOutRest(boolean)
	 */
	@Override
	public void setUseAssoGoOutRest(boolean val) {
		this.entity.setUserAssoGoOutRest(BooleanGetAtr.getAtrByBoolean(val));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetSetMemento#
	 * setCalculateMethod(nts.uk.ctx.at.shared.dom.worktime.common.
	 * FlowFixedRestCalcMethod)
	 */
	@Override
	public void setCalculateMethod(FlowFixedRestCalcMethod method) {
		this.entity.setFixedRestCalcMethod(method.value);
	}

}
