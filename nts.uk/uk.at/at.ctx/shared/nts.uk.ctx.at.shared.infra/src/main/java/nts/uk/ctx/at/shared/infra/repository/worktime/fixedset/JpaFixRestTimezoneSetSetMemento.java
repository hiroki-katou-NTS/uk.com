/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class JpaCoreTimeSettingGetMemento.
 */
public class JpaFixRestTimezoneSetSetMemento<T extends UkJpaEntity> implements FixRestTimezoneSetSetMemento {
	
	private List<T> entitySets;
	
	public JpaFixRestTimezoneSetSetMemento(List<T> entitySets) {
		super();
		this.entitySets = entitySets;
	}

	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		// TODO Auto-generated method stub

	}
}
