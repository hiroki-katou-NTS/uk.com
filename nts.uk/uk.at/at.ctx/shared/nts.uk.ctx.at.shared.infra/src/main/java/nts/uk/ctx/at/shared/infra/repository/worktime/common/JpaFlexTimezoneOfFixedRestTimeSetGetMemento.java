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
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTs;

/**
 * The Class JpaFlexTimezoneOfFixedRestTimeSetGetMemento.
 */
public class JpaFlexTimezoneOfFixedRestTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento{
	
	/** The entitys. */
	private List<KshmtWtFleBrFiWekTs> entitys;
	

	/**
	 * Instantiates a new jpa flex timezone of fixed rest time set get memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaFlexTimezoneOfFixedRestTimeSetGetMemento(List<KshmtWtFleBrFiWekTs> entitys) {
		super();
		this.entitys = entitys;
	}





	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		if (CollectionUtil.isEmpty(this.entitys)) {
			return new ArrayList<>();
		}

		return this.entitys.stream().map(entity -> new DeductionTime(new JpaFlexHADeductionTimeGetMemento(entity)))
				.collect(Collectors.toList());

	}

}
