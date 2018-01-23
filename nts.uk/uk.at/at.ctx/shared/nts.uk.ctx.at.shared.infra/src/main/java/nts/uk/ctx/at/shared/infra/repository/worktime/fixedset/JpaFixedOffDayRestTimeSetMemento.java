/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaFixedOffDayRestTimeSetMemento.
 */
public class JpaFixedOffDayRestTimeSetMemento implements FixRestTimezoneSetSetMemento {

	/** The entity. */
	private KshmtFixedWorkSet entity;
	
	/**
	 * Instantiates a new jpa fixed off day rest time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixedOffDayRestTimeSetMemento(KshmtFixedWorkSet entity) {
		this.entity = entity;
		if(entity.getKshmtFixedWorkSetPK() == null){
			entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		List<KshmtFixedHolRestSet> newListEntity = new ArrayList<>();

		if (!CollectionUtil.isEmpty(lstTimezone)) {
			String companyId = this.entity.getKshmtFixedWorkSetPK().getCid();
			String workTimeCd = this.entity.getKshmtFixedWorkSetPK().getWorktimeCd();

			// convert map entity
			if (CollectionUtil.isEmpty(this.entity.getLstKshmtFixedHolRestSet())) {
				this.entity.setLstKshmtFixedHolRestSet(new ArrayList<>());
			}
			Map<KshmtFixedHolRestSetPK, KshmtFixedHolRestSet> mapEntity = this.entity.getLstKshmtFixedHolRestSet()
					.stream()
					.collect(Collectors.toMap(item -> ((KshmtFixedHolRestSet) item).getKshmtFixedHolRestSetPK(),
							Function.identity()));

			for (DeductionTime time : lstTimezone) {
				KshmtFixedHolRestSetPK pk = new KshmtFixedHolRestSetPK(companyId, workTimeCd, lstTimezone.indexOf(time));
				
				// get entity existed
				KshmtFixedHolRestSet entity = mapEntity.get(pk) == null ? new KshmtFixedHolRestSet(pk) : mapEntity.get(pk);

				// set data
				entity.setStartTime(time.getStart().v());
				entity.setEndTime(time.getEnd().v());

				// add list
				newListEntity.add(entity);
			}
		}
		this.entity.setLstKshmtFixedHolRestSet(newListEntity);
	}

}
