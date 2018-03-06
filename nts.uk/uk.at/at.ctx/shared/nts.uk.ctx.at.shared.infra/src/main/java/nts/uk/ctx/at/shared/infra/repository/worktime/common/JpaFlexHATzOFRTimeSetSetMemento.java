/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRest;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaFixRestPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexHaRtSet;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexHATzOFRTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
	
	/** The entitys. */
	private KshmtFlexHaRtSet entity;
	
	/** The period no. */
	private int periodNo = 1;


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
		
		// check list entity get empty
		if (CollectionUtil.isEmpty(this.entity.getKshmtFlexHaFixRests())) {
			this.entity.setKshmtFlexHaFixRests(new ArrayList<>());
		}
		
		// check input empty
		if (CollectionUtil.isEmpty(timzones)) {
			this.entity.setKshmtFlexHaFixRests(new ArrayList<>());
			return;
		}
		
		// convert map entity
		Map<KshmtFlexHaFixRestPK, KshmtFlexHaFixRest> mapEntity = this.entity.getKshmtFlexHaFixRests().stream()
				.collect(Collectors.toMap(entity -> ((KshmtFlexHaFixRest) entity).getKshmtFlexHaFixRestPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtFlexHaRtSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd();
		Integer amPmAtr = this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr();
		
		// set list entity
		this.entity.setKshmtFlexHaFixRests(timzones.stream().map(domain -> {
			
			// newPk
			KshmtFlexHaFixRestPK pk = new KshmtFlexHaFixRestPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setPeriodNo(periodNo);
			
			// find entity existed if not have, new entity
			KshmtFlexHaFixRest entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtFlexHaFixRest(pk);
			}
			
			// save to memento
			domain.saveToMemento(new JpaFlexHADeductionTimeSetMemento(entity));
			
			// increase period no
			periodNo++;
			
			return entity;
		}).collect(Collectors.toList()));
	}

}
