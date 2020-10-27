/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.shortworktime;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento;
import nts.uk.ctx.at.shared.infra.entity.shortworktime.KshmtShorttimeHistItem;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaSWorkTimeHistItemGetMemento.
 */
public class JpaSWorkTimeHistItemGetMemento implements SWorkTimeHistItemGetMemento {

	/** The entity. */
	private KshmtShorttimeHistItem entity;

	/**
	 * Instantiates a new jpa S work time hist item get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSWorkTimeHistItemGetMemento(KshmtShorttimeHistItem entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento#
	 * getHistoryId()
	 */
	@Override
	public String getHistoryId() {
		return this.entity.getKshmtShorttimeHistItemPK().getHistId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento#
	 * getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.entity.getKshmtShorttimeHistItemPK().getSid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento#
	 * getChildCareAtr()
	 */
	@Override
	public ChildCareAtr getChildCareAtr() {
		return ChildCareAtr.valueOf(this.entity.getChildCareAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemGetMemento#
	 * getLstTimeZone()
	 */
	@Override
	public List<SChildCareFrame> getLstTimeSlot() {
		return this.entity.getLstKshmtShorttimeTs().stream().map(entity -> {
			return SChildCareFrame.builder()
					.timeSlot(entity.getKshmtShorttimeTsPK().getTimeNo())
					.startTime(new TimeWithDayAttr(entity.getStrClock()))
					.endTime(new TimeWithDayAttr(entity.getEndClock()))
					.build();
		}).collect(Collectors.toList());
	}

}
