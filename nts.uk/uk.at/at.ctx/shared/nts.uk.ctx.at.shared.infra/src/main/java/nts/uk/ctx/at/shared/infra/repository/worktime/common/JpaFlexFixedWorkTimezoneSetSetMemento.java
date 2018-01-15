/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexHaRtSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOtTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexOtTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtFlexWorkTimeSetPK;

/**
 * The Class JpaFlexFixedWorkTimezoneSetSetMemento.
 */
public class JpaFlexFixedWorkTimezoneSetSetMemento implements FixedWorkTimezoneSetSetMemento {

	/** The entity. */
	private KshmtFlexHaRtSet entity;

	/** The time frame no. */
	private int timeFrameNo;

	/** The worktime no. */
	private int worktimeNo;

	/**
	 * Instantiates a new jpa flex fixed work timezone set set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaFlexFixedWorkTimezoneSetSetMemento(KshmtFlexHaRtSet entity) {
		super();
		this.entity = entity;
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
		if (CollectionUtil.isEmpty(lstWorkingTimezone)) {
			this.entity.setKshmtFlexWorkTimeSets(new ArrayList<>());
		} else {
			timeFrameNo = 0;
			this.entity.setKshmtFlexWorkTimeSets(lstWorkingTimezone.stream().map(domain -> {
				timeFrameNo++;
				KshmtFlexWorkTimeSet entity = new KshmtFlexWorkTimeSet(
						new KshmtFlexWorkTimeSetPK(this.entity.getKshmtFlexHaRtSetPK().getCid(),
								this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd(),
								this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr(), timeFrameNo));
				domain.saveToMemento(new JpaFlexEmTimeZoneSetSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}
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
		if (CollectionUtil.isEmpty(lstOTTimezone)) {
			this.entity.setKshmtFlexOtTimeSets(new ArrayList<>());
		} else {
			worktimeNo = 0;
			this.entity.setKshmtFlexOtTimeSets(lstOTTimezone.stream().map(domain -> {
				worktimeNo++;
				KshmtFlexOtTimeSet entity = new KshmtFlexOtTimeSet(
						new KshmtFlexOtTimeSetPK(this.entity.getKshmtFlexHaRtSetPK().getCid(),
								this.entity.getKshmtFlexHaRtSetPK().getWorktimeCd(),
								this.entity.getKshmtFlexHaRtSetPK().getAmPmAtr(), worktimeNo));
				domain.saveToMemento(new JpaFlexOverTimeOfTimeZoneSetSetMemento(entity));
				return entity;
			}).collect(Collectors.toList()));
		}

	}

}
