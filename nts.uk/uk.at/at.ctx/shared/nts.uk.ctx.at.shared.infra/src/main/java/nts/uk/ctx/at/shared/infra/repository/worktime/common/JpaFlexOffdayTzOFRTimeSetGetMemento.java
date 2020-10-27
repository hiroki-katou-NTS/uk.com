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
import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtWtFleBrFiWekTsPK;

/**
 * The Class JpaFlexOffdayTzOFRTimeSetGetMemento.
 */
public class JpaFlexOffdayTzOFRTimeSetGetMemento implements TimezoneOfFixedRestTimeSetGetMemento{
	
	/** The entitys. */
	private List<KshmtWtFleBrFiWekTs> entitys;

	
	/**
	 * Instantiates a new jpa flex offday tz OFR time set get memento.
	 *
	 * @param entitys the entitys
	 */
	public JpaFlexOffdayTzOFRTimeSetGetMemento(List<KshmtWtFleBrFiWekTs> entitys) {
		super();
		this.entitys = entitys;
	}

	/**
	 * Sets the key entity.
	 *
	 * @param companyId the company id
	 * @param worktimeCode the worktime code
	 */
	public void setKeyEntity(String companyId, String worktimeCode){
		this.entitys.forEach(entity->{
			entity.setKshmtWtFleBrFiWekTsPK(new KshmtWtFleBrFiWekTsPK(companyId, worktimeCode));
		});
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * TimezoneOfFixedRestTimeSetGetMemento#getTimezones()
	 */
	@Override
	public List<DeductionTime> getTimezones() {
		if(CollectionUtil.isEmpty(this.entitys)){
			return new ArrayList<>();
		}
		return this.entitys.stream().map(entity -> new DeductionTime(new JpaFlexOffdayDeductionTimeGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
