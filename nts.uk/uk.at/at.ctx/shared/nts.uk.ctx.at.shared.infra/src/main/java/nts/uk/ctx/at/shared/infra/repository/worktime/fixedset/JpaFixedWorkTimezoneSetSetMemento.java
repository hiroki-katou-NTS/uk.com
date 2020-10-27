/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixWorkTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixWorkTsPK;

/**
 * The Class JpaFixedWorkTimezoneSetSetMemento.
 */
public class JpaFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The parent entitjy. */
	private KshmtWtFix parentEntity;
	
	/** The cid. */
	private String cid;

	/** The worktime cd. */
	private String worktimeCd;

	/** The type. */
	private int type;

	/**
	 * Instantiates a new jpa fixed work timezone set set memento.
	 *
	 * @param parentEntity the parent entitjy
	 */
	public JpaFixedWorkTimezoneSetSetMemento(KshmtWtFix parentEntity, int type) {
		super();
		this.parentEntity = parentEntity;
		this.type = type;
		
		// initial entity
		this.initialData();
	}

	/**
	 * Initial data.
	 */
	private void initialData() {
		this.cid = this.parentEntity.getKshmtWtFixPK().getCid();
		this.worktimeCd = this.parentEntity.getKshmtWtFixPK().getWorktimeCd();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento#
	 * setLstWorkingTimezone(java.util.List)
	 */
	@Override
	public void setLstWorkingTimezone(List<EmTimeZoneSet> lstWorkingTimezone) {
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtWtFixWorkTss())) {
			this.parentEntity.setKshmtWtFixWorkTss(new ArrayList<>());
		}
		
		List<KshmtWtFixWorkTs> otherList = this.parentEntity.getKshmtWtFixWorkTss().stream()
				.filter(entity -> entity.getKshmtWtFixWorkTsPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		
		// KSHMT_WT_FIX_WORK_TS
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			this.parentEntity.setKshmtWtFixWorkTss(otherList);
			return;
		}
		
		// get list entity
		List<KshmtWtFixWorkTs> lstEntity = this.parentEntity.getKshmtWtFixWorkTss().stream()
				.filter(entity -> entity.getKshmtWtFixWorkTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtWtFixWorkTs> newListEntity = new ArrayList<>();
		
		// sort asc start time
		lstWorkingTimezone.stream()
				.sorted((item1, item2) -> item1.getTimezone().getStart().v() - item2.getTimezone().getStart().v())
				.collect(Collectors.toList());
		
		for (EmTimeZoneSet domain : lstWorkingTimezone) {
			
			KshmtWtFixWorkTsPK pk = new KshmtWtFixWorkTsPK(this.cid, this.worktimeCd, this.type,
					domain.getEmploymentTimeFrameNo().v());
			
			// get entity existed
			KshmtWtFixWorkTs entity = lstEntity.stream()
					.filter(item -> item.getKshmtWtFixWorkTsPK().equals(pk))
					.findFirst()
					.orElse(new KshmtWtFixWorkTs(pk));
			
			// set data
			entity.setUnit(domain.getTimezone().getRounding().getRoundingTime().value);
			entity.setRounding(domain.getTimezone().getRounding().getRounding().value);
			entity.setTimeStr(domain.getTimezone().getStart().valueAsMinutes());
			entity.setTimeEnd(domain.getTimezone().getEnd().valueAsMinutes());
			
			// add list
			newListEntity.add(entity);
		}
		
		newListEntity.addAll(otherList);
		
		this.parentEntity.setKshmtWtFixWorkTss(newListEntity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento#
	 * setLstOTTimezone(java.util.List)
	 */
	@Override
	public void setLstOTTimezone(List<OverTimeOfTimeZoneSet> lstOTTimezone) {
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtWtFixOverTss())) {
			this.parentEntity.setKshmtWtFixOverTss(new ArrayList<>());
		}
		
		List<KshmtWtFixOverTs> otherList = this.parentEntity.getKshmtWtFixOverTss().stream()
				.filter(entity -> entity.getKshmtWtFixOverTsPK().getAmPmAtr() != this.type)
				.collect(Collectors.toList());
		
		// KSHMT_WT_FIX_OVER_TS
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			this.parentEntity.setKshmtWtFixOverTss(otherList);
			return;
		}
		
		// get list entity
		List<KshmtWtFixOverTs> lstEntity = this.parentEntity.getKshmtWtFixOverTss().stream()
				.filter(entity -> entity.getKshmtWtFixOverTsPK().getAmPmAtr() == this.type)
				.collect(Collectors.toList());
		if (CollectionUtil.isEmpty(lstEntity)) {
			lstEntity = new ArrayList<>();
		}
		
		List<KshmtWtFixOverTs> newListEntity = new ArrayList<>();
		
		// sort asc start time
		lstOTTimezone.stream()
				.sorted((item1, item2) -> item1.getTimezone().getStart().v() - item2.getTimezone().getStart().v())
				.collect(Collectors.toList());
		
		for (OverTimeOfTimeZoneSet domain : lstOTTimezone) {
			
			KshmtWtFixOverTsPK pk = new KshmtWtFixOverTsPK(this.cid, this.worktimeCd, this.type,
					domain.getWorkTimezoneNo().v());
			
			// get entity existed
			KshmtWtFixOverTs entity = lstEntity.stream()
					.filter(item -> item.getKshmtWtFixOverTsPK().equals(pk))
					.findFirst()
					.orElse(new KshmtWtFixOverTs(pk));
			
			// set data
			domain.saveToMemento(new JpaFixOverTimeOfTimeZoneSetSetMemento(entity));
			
			// add list
			newListEntity.add(entity);
		}
		
		newListEntity.addAll(otherList);
		
		this.parentEntity.setKshmtWtFixOverTss(newListEntity);
	}

}
