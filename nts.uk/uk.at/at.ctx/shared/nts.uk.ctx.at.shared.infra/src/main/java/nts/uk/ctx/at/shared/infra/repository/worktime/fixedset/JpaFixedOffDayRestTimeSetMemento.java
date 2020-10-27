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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixPK;

/**
 * The Class JpaFixedOffDayRestTimeSetMemento.
 */
public class JpaFixedOffDayRestTimeSetMemento implements FixRestTimezoneSetSetMemento {

	/** The entity. */
	private KshmtWtFix entity;
	
	/**
	 * Instantiates a new jpa fixed off day rest time set memento.
	 *
	 * @param entity the entity
	 */
	public JpaFixedOffDayRestTimeSetMemento(KshmtWtFix entity) {
		this.entity = entity;
		if(entity.getKshmtWtFixPK() == null){
			entity.setKshmtWtFixPK(new KshmtWtFixPK());
		}
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		List<KshmtWtFixBrHolTs> newListEntity = new ArrayList<>();

		if (!CollectionUtil.isEmpty(lstTimezone)) {
			String companyId = this.entity.getKshmtWtFixPK().getCid();
			String workTimeCd = this.entity.getKshmtWtFixPK().getWorktimeCd();

			// convert map entity
			if (CollectionUtil.isEmpty(this.entity.getLstKshmtWtFixBrHolTs())) {
				this.entity.setLstKshmtWtFixBrHolTs(new ArrayList<>());
			}
			Map<KshmtWtFixBrHolTsPK, KshmtWtFixBrHolTs> mapEntity = this.entity.getLstKshmtWtFixBrHolTs()
					.stream()
					.collect(Collectors.toMap(item -> ((KshmtWtFixBrHolTs) item).getKshmtWtFixBrHolTsPK(),
							Function.identity()));

			for (DeductionTime time : lstTimezone) {
				KshmtWtFixBrHolTsPK pk = new KshmtWtFixBrHolTsPK(companyId, workTimeCd, lstTimezone.indexOf(time));
				
				// get entity existed
				KshmtWtFixBrHolTs entity = mapEntity.get(pk) == null ? new KshmtWtFixBrHolTs(pk) : mapEntity.get(pk);

				// set data
				entity.setStartTime(time.getStart().v());
				entity.setEndTime(time.getEnd().v());

				// add list
				newListEntity.add(entity);
			}
		}
		this.entity.setLstKshmtWtFixBrHolTs(newListEntity);
	}

}
