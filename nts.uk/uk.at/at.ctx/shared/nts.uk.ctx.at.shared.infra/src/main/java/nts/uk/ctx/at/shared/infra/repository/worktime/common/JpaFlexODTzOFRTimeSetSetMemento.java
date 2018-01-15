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
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdFixRestPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOdRtSet;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexODTzOFRTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
	
	/** The entitys. */
	private KshmtFlexOdRtSet entity;

	/** The period no. */
	private int periodNo = 0;

	/**
	 * Instantiates a new jpa flex OD tz OFR time set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexODTzOFRTimeSetSetMemento(KshmtFlexOdRtSet entity) {
		super();
		this.entity = entity;
	}


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento#setTimezones(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> timzones) {
		if (CollectionUtil.isEmpty(timzones)) {
			this.entity.setKshmtFlexOdFixRests(new ArrayList<>());
		} else {
			periodNo = 0;
			this.entity.setKshmtFlexOdFixRests(timzones.stream().map(domain -> {
				periodNo++;
				KshmtFlexOdFixRest entity = new KshmtFlexOdFixRest(
						new KshmtFlexOdFixRestPK(this.entity.getKshmtFlexOdRtSetPK().getCid(),
								this.entity.getKshmtFlexOdRtSetPK().getWorktimeCd(), periodNo));
				domain.saveToMemento(new JpaFlexODDeductionTimeSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}

	}

}
