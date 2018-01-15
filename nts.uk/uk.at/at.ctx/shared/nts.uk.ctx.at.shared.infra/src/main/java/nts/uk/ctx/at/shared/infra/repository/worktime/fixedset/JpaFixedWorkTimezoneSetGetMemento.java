/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedOtTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkTimeSet;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedWorkTimezoneSetGetMemento.
 */
public class JpaFixedWorkTimezoneSetGetMemento implements FixedWorkTimezoneSetGetMemento {

	/** The kshmt fixed work time sets. */
	// KSHMT_FIXED_WORK_TIME_SET 就業時間の時間帯設定(固定)
	private List<KshmtFixedWorkTimeSet> kshmtFixedWorkTimeSets;

	/** The kshmt fixed ot time sets. */
	// KSHMT_FIXED_OT_TIME_SET 残業時間の時間帯設定
	private List<KshmtFixedOtTimeSet> kshmtFixedOtTimeSets;

	/**
	 * Instantiates a new jpa fixed work timezone set get memento.
	 *
	 * @param kshmtFixedWorkTimeSets
	 *            the kshmt fixed work time sets
	 * @param kshmtFixedOtTimeSets
	 *            the kshmt fixed ot time sets
	 */
	public JpaFixedWorkTimezoneSetGetMemento(List<KshmtFixedWorkTimeSet> kshmtFixedWorkTimeSets,
			List<KshmtFixedOtTimeSet> kshmtFixedOtTimeSets) {
		super();		
		this.kshmtFixedWorkTimeSets = kshmtFixedWorkTimeSets;
		this.kshmtFixedOtTimeSets = kshmtFixedOtTimeSets;
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
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#
	 * getLstWorkingTimezone()
	 */
	@Override
	public List<EmTimeZoneSet> getLstWorkingTimezone() {
		return this.kshmtFixedWorkTimeSets.stream()
				.map(item -> new EmTimeZoneSet(
						new EmTimeFrameNo(item.getKshmtFixedWorkTimeSetPK().getTimeFrameNo()),
						new TimeZoneRounding(new TimeWithDayAttr(item.getTimeStr()),
						new TimeWithDayAttr(item.getTimeEnd()), new TimeRoundingSetting(
								item.getUnit(), 
								item.getRounding()))
						)
				)
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetGetMemento#
	 * getLstOTTimezone()
	 */
	@Override
	public List<OverTimeOfTimeZoneSet> getLstOTTimezone() {
		return this.kshmtFixedOtTimeSets.stream()
				.map(item -> new OverTimeOfTimeZoneSet(new JpaFixOverTimeOfTimeZoneSetGetMemento(item)))
				.collect(Collectors.toList());
	}

}
