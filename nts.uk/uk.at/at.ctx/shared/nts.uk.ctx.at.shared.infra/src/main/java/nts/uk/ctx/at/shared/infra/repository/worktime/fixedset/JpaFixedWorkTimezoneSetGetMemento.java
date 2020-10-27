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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixOverTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixWorkTs;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaFixedWorkTimezoneSetGetMemento.
 */
public class JpaFixedWorkTimezoneSetGetMemento implements FixedWorkTimezoneSetGetMemento {

	/** The kshmt fixed work time sets. */
	// KSHMT_WT_FIX_WORK_TS 就業時間の時間帯設定(固定)
	private List<KshmtWtFixWorkTs> kshmtWtFixWorkTss;

	/** The kshmt fixed ot time sets. */
	// KSHMT_WT_FIX_OVER_TS 残業時間の時間帯設定
	private List<KshmtWtFixOverTs> kshmtWtFixOverTss;

	/**
	 * Instantiates a new jpa fixed work timezone set get memento.
	 *
	 * @param kshmtWtFixWorkTss
	 *            the kshmt fixed work time sets
	 * @param kshmtWtFixOverTss
	 *            the kshmt fixed ot time sets
	 */
	public JpaFixedWorkTimezoneSetGetMemento(List<KshmtWtFixWorkTs> kshmtWtFixWorkTss,
			List<KshmtWtFixOverTs> kshmtWtFixOverTss) {
		super();		
		this.kshmtWtFixWorkTss = kshmtWtFixWorkTss;
		this.kshmtWtFixOverTss = kshmtWtFixOverTss;
		if (CollectionUtil.isEmpty(this.kshmtWtFixWorkTss)) {
			this.kshmtWtFixWorkTss = new ArrayList<>();
		}
		if (CollectionUtil.isEmpty(this.kshmtWtFixOverTss)) {
			this.kshmtWtFixOverTss = new ArrayList<>();
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
		return this.kshmtWtFixWorkTss.stream()
				.map(item -> new EmTimeZoneSet(
						new EmTimeFrameNo(item.getKshmtWtFixWorkTsPK().getTimeFrameNo()),
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
		return this.kshmtWtFixOverTss.stream()
				.map(item -> new OverTimeOfTimeZoneSet(new JpaFixOverTimeOfTimeZoneSetGetMemento(item)))
				.collect(Collectors.toList());
	}

}
