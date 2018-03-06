/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRest;

/**
 * The Class JpaFlexTimezoneOfFixedRestTimeSetSetMemento.
 */
public class JpaFlexTimezoneOfFixedRestTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
	
	/** The entitys. */
	private List<KshmtFlexHaFixRest> entitys;
	


	/**
	 * Instantiates a new jpa flex timezone of fixed rest time set set memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaFlexTimezoneOfFixedRestTimeSetSetMemento(List<KshmtFlexHaFixRest> entitys) {
		super();
		this.entitys = entitys;
	}




	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if (CollectionUtil.isEmpty(timzones)) {
			this.entitys = new ArrayList<>();
		} else {
			this.entitys = timzones.stream().map(domain -> {
				KshmtFlexHaFixRest entity = new KshmtFlexHaFixRest();
				domain.saveToMemento(new JpaFlexHADeductionTimeSetMemento(entity));
				return entity;
			}).collect(Collectors.toList());
		}
	}


}
