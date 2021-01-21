/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestClockCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFlAll;

/**
 * The Class JpaFlowRestSetGetMemento.
 */
public class JpaFlowRestSetGetMemento implements FlowRestSetGetMemento {
	
	/** The entity. */
	KshmtWtFloBrFlAll entity;
	
	/**
	 * Instantiates a new jpa flow rest set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowRestSetGetMemento(KshmtWtFloBrFlAll entity) {
		super();
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getUseStamp()
	 */
	@Override
	public boolean getUseStamp() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getUseStamp());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getUseStampCalcMethod()
	 */
	@Override
	public FlowRestClockCalcMethod getUseStampCalcMethod() {
		return FlowRestClockCalcMethod.valueOf(this.entity.getUseStampCalcMethod());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getTimeManagerSetAtr()
	 */
	@Override
	public RestClockManageAtr getTimeManagerSetAtr() {
		return RestClockManageAtr.valueOf(this.entity.getTimeManagerSetAtr());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetGetMemento#getCalculateMethod()
	 */
	@Override
	public FlowRestCalcMethod getCalculateMethod() {
		return FlowRestCalcMethod.valueOf(this.entity.getFixedCalculateMethod());
	}

}
