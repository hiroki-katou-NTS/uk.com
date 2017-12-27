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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSetPK;

/**
 * The Class JpaFixedWorkTimezoneSetSetMemento.
 */
public class JpaFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The kshmt fixed work time sets. */
	// KSHMT_FIXED_WORK_TIME_SET 就業時間の時間帯設定(固定)
	private List<KshmtFixedWorkTimeSet> kshmtFixedWorkTimeSets;

	/** The kshmt fixed ot time sets. */
	// KSHMT_FIXED_OT_TIME_SET 残業時間の時間帯設定
	private List<KshmtFixedOtTimeSet> kshmtFixedOtTimeSets;

	/** The cid. */
	private String cid;

	/** The worktime cd. */
	private String worktimeCd;

	/** The type. */
	private int type;

	/**
	 * Instantiates a new jpa fixed work timezone set set memento.
	 *
	 * @param kshmtFixedWorkTimeSets
	 *            the kshmt fixed work time sets
	 * @param kshmtFixedOtTimeSets
	 *            the kshmt fixed ot time sets
	 * @param cid
	 *            the cid
	 * @param worktimeCd
	 *            the worktime cd
	 * @param type
	 *            the type
	 */
	public JpaFixedWorkTimezoneSetSetMemento(List<KshmtFixedWorkTimeSet> kshmtFixedWorkTimeSets,
			List<KshmtFixedOtTimeSet> kshmtFixedOtTimeSets, String cid, String worktimeCd, int type) {
		this.kshmtFixedWorkTimeSets = kshmtFixedWorkTimeSets;
		this.kshmtFixedOtTimeSets = kshmtFixedOtTimeSets;
		this.cid = cid;
		this.worktimeCd = worktimeCd;
		this.type = type;
		if (CollectionUtil.isEmpty(this.kshmtFixedWorkTimeSets)) {
			this.kshmtFixedWorkTimeSets = new ArrayList<>();
		}
		if (CollectionUtil.isEmpty(this.kshmtFixedOtTimeSets)) {
			this.kshmtFixedOtTimeSets = new ArrayList<>();
		}
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
		Map<KshmtFixedWorkTimeSetPK, KshmtFixedWorkTimeSet> currentSets = this.kshmtFixedWorkTimeSets.stream()
				.map(KshmtFixedWorkTimeSet.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtFixedWorkTimeSetPK(), Function.identity()));

		this.kshmtFixedWorkTimeSets.addAll(lstWorkingTimezone.stream()
				.map(domain -> {
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
					return entity;
				})
				.collect(Collectors.toList()));
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
		Map<KshmtFixedOtTimeSetPK, KshmtFixedOtTimeSet> currentSets = this.kshmtFixedOtTimeSets.stream()
				.map(KshmtFixedOtTimeSet.class::cast)
				.collect(Collectors.toMap(entity -> entity.getKshmtFixedOtTimeSetPK(), Function.identity()));

		this.kshmtFixedOtTimeSets.addAll(lstOTTimezone.stream()
				.map(domain -> {		
					KshmtFixedOtTimeSetPK pk = new KshmtFixedOtTimeSetPK(this.cid, this.worktimeCd, this.type, domain.getWorkTimezoneNo().v());				
					KshmtFixedOtTimeSet entity = currentSets.get(pk);
					if (entity == null) {
						entity = new KshmtFixedOtTimeSet();
						entity.setKshmtFixedOtTimeSetPK(pk);
					}			
					domain.saveToMemento(new JpaFixOverTimeOfTimeZoneSetSetMemento(entity));
					return entity;
				})
				.collect(Collectors.toList()));
	}

}
