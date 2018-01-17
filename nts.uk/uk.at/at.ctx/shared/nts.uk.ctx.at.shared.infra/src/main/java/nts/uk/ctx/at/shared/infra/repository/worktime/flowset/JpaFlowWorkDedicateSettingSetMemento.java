package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkDedSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowCalculateSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOTSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet;

public class JpaFlowWorkDedicateSettingSetMemento implements FlWorkDedSetMemento {
	
	/** The entity. */
	private KshmtFlowWorkSet entity;
	
	/**
	 * Instantiates a new jpa flow work dedicate setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlowWorkDedicateSettingSetMemento(KshmtFlowWorkSet entity) {
		super();
		this.entity = entity;
	}
	
	@Override
	public void setOvertimeSetting(FlowOTSet otSet) {
		otSet.saveToMemento(new JpaFlowOTSetSetMemento(this.entity));
	}

	@Override
	public void setCalculateSetting(FlowCalculateSet fcSet) {
		// TODO Auto-generated method stub
		
	}

}
