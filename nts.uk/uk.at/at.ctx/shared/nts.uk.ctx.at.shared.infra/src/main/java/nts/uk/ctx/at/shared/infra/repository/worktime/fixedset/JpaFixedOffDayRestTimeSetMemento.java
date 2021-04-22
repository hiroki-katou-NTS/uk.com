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
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;

/**
 * The Class JpaFixedOffDayRestTimeSetMemento.
 */
public class JpaFixedOffDayRestTimeSetMemento implements TimezoneOfFixedRestTimeSetSetMemento {

	/** The entity. */
	private KshmtWtFix entity;
	
	/**
	 * Instantiates a new jpa fixed off day rest time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixedOffDayRestTimeSetMemento(KshmtWtFix entity) {
		this.entity = entity;
		if(entity.getKshmtFixedWorkSetPK() == null){
			entity.setKshmtFixedWorkSetPK(new KshmtFixedWorkSetPK());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setTimezones(List<DeductionTime> lstTimezone) {
		List<KshmtWtFixBrHolTs> newListEntity = new ArrayList<>();

		if (!CollectionUtil.isEmpty(lstTimezone)) {
			String companyId = this.entity.getKshmtFixedWorkSetPK().getCid();
			String workTimeCd = this.entity.getKshmtFixedWorkSetPK().getWorktimeCd();

			// convert map entity
			if (CollectionUtil.isEmpty(this.entity.getLstKshmtFixedHolRestSet())) {
				this.entity.setLstKshmtFixedHolRestSet(new ArrayList<>());
			}
			Map<KshmtFixedHolRestSetPK, KshmtWtFixBrHolTs> mapEntity = this.entity.getLstKshmtFixedHolRestSet()
					.stream()
					.collect(Collectors.toMap(item -> ((KshmtWtFixBrHolTs) item).getKshmtFixedHolRestSetPK(),
							Function.identity()));

			for (DeductionTime time : lstTimezone) {
				KshmtFixedHolRestSetPK pk = new KshmtFixedHolRestSetPK(companyId, workTimeCd, lstTimezone.indexOf(time));
				
				// get entity existed
				KshmtWtFixBrHolTs entity = mapEntity.get(pk) == null ? new KshmtWtFixBrHolTs(pk) : mapEntity.get(pk);

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
