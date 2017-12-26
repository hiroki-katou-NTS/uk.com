/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;

/**
 * The Class JpaFixedOffDayRestTimeSetMemento.
 */
public class JpaFixedOffDayRestTimeSetMemento implements FixRestTimezoneSetSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;
	
	/** The Constant EQUAL. */
	private static final Integer EQUAL = 0;
	
	/**
	 * Instantiates a new jpa fixed off day rest time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixedOffDayRestTimeSetMemento(KshmtFixedWorkSet entity) {
		this.entity = entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		
		String companyId = this.entity.getKshmtFixedWorkSetPK().getCid();
		String workTimeCd = this.entity.getKshmtFixedWorkSetPK().getWorktimeCd();
		
		// get list entity
		List<KshmtFixedHolRestSet> lstEntity = this.entity.getLstKshmtFixedHolRestSet();
		
		List<KshmtFixedHolRestSet> newListEntity = new ArrayList<>();
		
		for (DeductionTime time : lstTimezone) {
			
			// get entity existed
			KshmtFixedHolRestSet entity = lstEntity.stream().filter(item -> {
				KshmtFixedHolRestSetPK pk = item.getKshmtFixedHolRestSetPK();
						return pk.getCid().compareTo(companyId) == EQUAL
								&& pk.getWorktimeCd().compareTo(workTimeCd) == EQUAL;
					})
					.findFirst()
					.orElse(new KshmtFixedHolRestSet(companyId, workTimeCd));
			
			// set data
			entity.setStartTime(time.getStart().v());
			entity.setEndTime(time.getEnd().v());
			
			// add list
			newListEntity.add(entity);
		}
		this.entity.setLstKshmtFixedHolRestSet(newListEntity);
	}

}
