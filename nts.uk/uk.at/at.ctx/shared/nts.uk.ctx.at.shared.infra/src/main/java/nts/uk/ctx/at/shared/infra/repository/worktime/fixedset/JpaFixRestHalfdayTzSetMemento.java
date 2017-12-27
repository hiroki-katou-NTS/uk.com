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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSetPK;

/**
 * The Class JpaFixRestHalfdayTzSetMemento.
 */
public class JpaFixRestHalfdayTzSetMemento implements FixRestTimezoneSetSetMemento {

	/** The entity sets. */
	private List<KshmtFixedHalfRestSet> entitySets;
	
	/** The cid. */
	private String cid;
	
	/** The worktime cd. */
	private String worktimeCd;
	
	/** The type. */
	private int type;

	/**
	 * Instantiates a new jpa fix rest halfday tz set memento.
	 *
	 * @param entitySets
	 *            the entity sets
	 */
	public JpaFixRestHalfdayTzSetMemento(List<KshmtFixedHalfRestSet> entitySets, String cid, String worktimeCd, int type) {
		super();
		this.entitySets = entitySets;
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.type = type;
		if (CollectionUtil.isEmpty(this.entitySets)) {
			this.entitySets = new ArrayList<>();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento#
	 * setLstTimezone(java.util.List)
	 */
	@Override
	public void setLstTimezone(List<DeductionTime> lstTimezone) {
		// KSHMT_FIXED_HALF_REST_SET
		if (CollectionUtil.isEmpty(lstTimezone)) {
			return;
		}
		Map<KshmtFixedHalfRestSetPK, KshmtFixedHalfRestSet> currentSets = this.entitySets.stream()
				.map(KshmtFixedHalfRestSet.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtFixedHalfRestSetPK(), Function.identity()));

		this.entitySets.addAll(lstTimezone.stream()
				.map(domain -> {
					KshmtFixedHalfRestSetPK pk = new KshmtFixedHalfRestSetPK(this.cid, this.worktimeCd, this.type);				
					KshmtFixedHalfRestSet entity = currentSets.get(pk);
					if (entity == null) {
						entity = new KshmtFixedHalfRestSet();
						entity.setKshmtFixedHalfRestSetPK(pk);
					}			
					domain.saveToMemento(new JpaFixedRestTZDeductionTimeSetMemento<KshmtFixedHalfRestSet>(entity));
					return entity;
				})
				.collect(Collectors.toList()));
	}
}
