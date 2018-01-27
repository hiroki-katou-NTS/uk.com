/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowFixedRestSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexRestSetPK;

/**
 * The Class JpaFlexFlowFixedRestSetGetMemento.
 */
public class JpaFlexFlowFixedRestSetGetMemento implements FlowFixedRestSetGetMemento{

	/** The entity. */
	private KshmtFlexRestSet entity;
	
	
	/**
	 * Instantiates a new jpa flex flow fixed rest set get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexFlowFixedRestSetGetMemento(KshmtFlexRestSet entity) {
		super();
		if(entity.getKshmtFlexRestSetPK() == null){
			entity.setKshmtFlexRestSetPK(new KshmtFlexRestSetPK());
		}
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
