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
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedOtTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSetPK;

/**
 * The Class JpaFixedWorkTimezoneSetSetMemento.
 */
public class JpaFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The parent entitjy. */
	private KshmtFixedWorkSet parentEntity;
	
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
	public JpaFixedWorkTimezoneSetSetMemento(KshmtFixedWorkSet parentEntity, int type) {
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
		this.cid = this.parentEntity.getKshmtFixedWorkSetPK().getCid();
		this.worktimeCd = this.parentEntity.getKshmtFixedWorkSetPK().getWorktimeCd();
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
		// KSHMT_FIXED_WORK_TIME_SET
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			return;
		}
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtFixedWorkTimeSets())) {
			this.parentEntity.setKshmtFixedWorkTimeSets(new ArrayList<>());
		}
		
		Map<KshmtFixedWorkTimeSetPK, KshmtFixedWorkTimeSet> currentSets = this.parentEntity.getKshmtFixedWorkTimeSets()
				.stream()
				.map(KshmtFixedWorkTimeSet.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtFixedWorkTimeSetPK(), Function.identity()));
		
		lstWorkingTimezone.forEach(domain -> {
			KshmtFixedWorkTimeSetPK pk = new KshmtFixedWorkTimeSetPK(this.cid, this.worktimeCd, this.type, domain.getEmploymentTimeFrameNo().v());				
			KshmtFixedWorkTimeSet entity = currentSets.get(pk);
			if (entity == null) {
				entity = new KshmtFixedWorkTimeSet();
				entity.setKshmtFixedWorkTimeSetPK(pk);
			}					
			entity.setUnit(domain.getTimezone().getRounding().getRoundingTime().value);
			entity.setRounding(domain.getTimezone().getRounding().getRounding().value);
			entity.setTimeStr(domain.getTimezone().getStart().valueAsMinutes());
			entity.setTimeEnd(domain.getTimezone().getEnd().valueAsMinutes());
			currentSets.put(entity.getKshmtFixedWorkTimeSetPK(), entity);
		});

		this.parentEntity.setKshmtFixedWorkTimeSets(currentSets.values().stream().collect(Collectors.toList()));
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
		// KSHMT_FIXED_OT_TIME_SET
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			return;
		}
		
		if (CollectionUtil.isEmpty(this.parentEntity.getKshmtFixedOtTimeSets())) {
			this.parentEntity.setKshmtFixedOtTimeSets(new ArrayList<>());
		}
		
		Map<KshmtFixedOtTimeSetPK, KshmtFixedOtTimeSet> currentSets = this.parentEntity.getKshmtFixedOtTimeSets().stream()
				.map(KshmtFixedOtTimeSet.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtFixedOtTimeSetPK(), Function.identity()));
		
		lstOTTimezone.forEach(domain -> {
			KshmtFixedOtTimeSetPK pk = new KshmtFixedOtTimeSetPK(this.cid, this.worktimeCd, this.type, domain.getWorkTimezoneNo().v());				
			KshmtFixedOtTimeSet entity = currentSets.get(pk);
			if (entity == null) {
				entity = new KshmtFixedOtTimeSet();
				entity.setKshmtFixedOtTimeSetPK(pk);
			}			
			domain.saveToMemento(new JpaFixOverTimeOfTimeZoneSetSetMemento(entity));
			currentSets.put(entity.getKshmtFixedOtTimeSetPK(), entity);
		});

		this.parentEntity.setKshmtFixedOtTimeSets(currentSets.values().stream().collect(Collectors.toList()));
	}

}
