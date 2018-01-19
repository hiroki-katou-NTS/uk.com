/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowRestSet;

/**
 * The Class JpaFlowFixedRestSetGetMemento.
 */
public class JpaFlowFixedRestSetGetMemento implements FlowFixedRestSetGetMemento {
	
	/** The entity. */
	KshmtFlowRestSet entity;
	
	/**
	 * Instantiates a new jpa flow fixed rest set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowFixedRestSetGetMemento(KshmtFlowRestSet entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getIsReferRestTime()
	 */
	@Override
	public boolean getIsReferRestTime() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getIsReferRestTime());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getUsePrivateGoOutRest()
	 */
	@Override
	public boolean getUsePrivateGoOutRest() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUserPrivateGoOutRest());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getUseAssoGoOutRest()
	 */
	@Override
	public boolean getUseAssoGoOutRest() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUserAssoGoOutRest());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowFixedRestSetGetMemento#getCalculateMethod()
	 */
	@Override
	public FlowFixedRestCalcMethod getCalculateMethod() {
		return FlowFixedRestCalcMethod.valueOf(this.entity.getFixedRestCalcMethod());
	}

}
