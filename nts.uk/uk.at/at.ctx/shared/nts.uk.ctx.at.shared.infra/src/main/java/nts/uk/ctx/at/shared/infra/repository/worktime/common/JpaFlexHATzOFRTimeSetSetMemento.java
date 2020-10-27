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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFlWek;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexHATzOFRTimeSetSetMemento implements TimezoneOfFixedRestTimeSetSetMemento{
	
	/** The entitys. */
	private KshmtWtFleBrFlWek entity;
	
	/** The period no. */
	private int periodNo = 1;


	/**
	 * Instantiates a new jpa flex HA tz OFR time set set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFlexHATzOFRTimeSetSetMemento(KshmtWtFleBrFlWek entity) {
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
		if (CollectionUtil.isEmpty(this.entity.getKshmtWtFleBrFiWekTss())) {
			this.entity.setKshmtWtFleBrFiWekTss(new ArrayList<>());
		}
		
		// check input empty
		if (CollectionUtil.isEmpty(timzones)) {
			this.entity.setKshmtWtFleBrFiWekTss(new ArrayList<>());
			return;
		}
		
		// convert map entity
		Map<KshmtWtFleBrFiWekTsPK, KshmtWtFleBrFiWekTs> mapEntity = this.entity.getKshmtWtFleBrFiWekTss().stream()
				.collect(Collectors.toMap(entity -> ((KshmtWtFleBrFiWekTs) entity).getKshmtWtFleBrFiWekTsPK(),
						Function.identity()));
		
		String companyId = this.entity.getKshmtWtFleBrFlWekPK().getCid();
		String workTimeCd = this.entity.getKshmtWtFleBrFlWekPK().getWorktimeCd();
		Integer amPmAtr = this.entity.getKshmtWtFleBrFlWekPK().getAmPmAtr();
		
		// set list entity
		this.entity.setKshmtWtFleBrFiWekTss(timzones.stream().map(domain -> {
			
			// newPk
			KshmtWtFleBrFiWekTsPK pk = new KshmtWtFleBrFiWekTsPK();
			pk.setCid(companyId);
			pk.setWorktimeCd(workTimeCd);
			pk.setAmPmAtr(amPmAtr);
			pk.setPeriodNo(periodNo);
			
			// find entity existed if not have, new entity
			KshmtWtFleBrFiWekTs entity = mapEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFleBrFiWekTs(pk);
			}
			
			// save to memento
			domain.saveToMemento(new JpaFlexHADeductionTimeSetMemento(entity));
			
			// increase period no
			periodNo++;
			
			return entity;
		}).collect(Collectors.toList()));
	}

}
