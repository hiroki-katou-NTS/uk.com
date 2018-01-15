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
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaFixRestPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaRtSet;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexHATzOFRTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
	
	/** The entitys. */
	private KshmtFlexHaRtSet entity;
	
	private int periodNo = 0;


	/**
	 * Instantiates a new jpa flex HA tz OFR time set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHATzOFRTimeSetSetMemento(KshmtFlexHaRtSet entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if (CollectionUtil.isEmpty(timzones)) {
			this.entity.setKshmtFlexHaFixRests(new ArrayList<>());
		} else {
			periodNo = 0;
			this.entity.setKshmtFlexHaFixRests(timzones.stream().map(domain -> {
				periodNo++;
				KshmtFlexHaFixRest entity = new KshmtFlexHaFixRest(
						new KshmtFlexHaFixRestPK(this.entity.getKshmtFlexHaRtSetPK().getCid(),
								this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd(),
								this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr(), periodNo));
				domain.saveToMemento(new JpaFlexHADeductionTimeSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}
	}

}
