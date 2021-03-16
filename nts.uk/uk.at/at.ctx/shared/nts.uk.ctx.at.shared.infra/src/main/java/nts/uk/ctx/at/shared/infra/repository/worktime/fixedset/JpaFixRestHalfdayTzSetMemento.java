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
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixRestTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;

/**
 * The Class JpaFixRestHalfdayTzSetMemento.
 */
public class JpaFixRestHalfdayTzSetMemento implements FixRestTimezoneSetSetMemento {

	/** The entity sets. */
	private KshmtWtFix entity;
	
	/** The cid. */
	private String cid;
	
	/** The worktime cd. */
	private String worktimeCd;
	
	/** The type. */
	private AmPmAtr type;
	
	/** The period no. */
	private int periodNo;

	/**
	 * Instantiates a new jpa fix rest halfday tz set memento.
	 *
	 * @param entitySets
	 *            the entity sets
	 */
	public JpaFixRestHalfdayTzSetMemento(KshmtWtFix entity, String cid, String worktimeCd, AmPmAtr type) {
		super();
		this.entity = entity;
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.type = type;
		if (CollectionUtil.isEmpty(this.entity.getKshmtFixedHalfRestSets())) {
			this.entity.setKshmtFixedHalfRestSets(new ArrayList<>());
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
		
		// Get settings with other type
		List<KshmtWtFixBrWekTs> otherList = this.entity.getKshmtFixedHalfRestSets().stream()
				.filter(entity -> entity.getKshmtFixedHalfRestSetPK().getAmPmAtr() != this.type.value)
				.collect(Collectors.toList());
				
		// KSHMT_WT_FIX_BR_WEK_TS
		if (CollectionUtil.isEmpty(lstTimezone)) {
			this.entity.setKshmtFixedHalfRestSets(otherList);
			return;
		}
		
		// get list old entity
		Map<KshmtFixedHalfRestSetPK, KshmtWtFixBrWekTs> lstOldEntity = this.entity.getKshmtFixedHalfRestSets().stream()
				.filter(entity -> entity.getKshmtFixedHalfRestSetPK().getAmPmAtr() == this.type.value)
				.collect(Collectors.toMap(KshmtWtFixBrWekTs::getKshmtFixedHalfRestSetPK, Function.identity()));
		
		List<KshmtWtFixBrWekTs> newListEntity = new ArrayList<>();
				
		periodNo = 0;		
		lstTimezone.forEach(domain -> {
			periodNo++;
			KshmtFixedHalfRestSetPK pk = new KshmtFixedHalfRestSetPK(this.cid, this.worktimeCd, this.type.value, periodNo);				
			KshmtWtFixBrWekTs entity = lstOldEntity.get(pk);
			if (entity == null) {
				entity = new KshmtWtFixBrWekTs();
				entity.setKshmtFixedHalfRestSetPK(pk);
			}						
			domain.saveToMemento(new JpaFixedRestTZDeductionTimeSetMemento<KshmtWtFixBrWekTs>(entity));
			
			// add list
			newListEntity.add(entity);
		});
		
		newListEntity.addAll(otherList);
		
		this.entity.setKshmtFixedHalfRestSets(newListEntity);
	}
}
