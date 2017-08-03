/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.dailypattern;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternVal;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.PatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.PatternName;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaDailyPatternGetMemento.
 */
public class JpaDailyPatternGetMemento implements DailyPatternGetMemento {

	/** The pattern calendar. */
	private KdpstDailyPatternSet patternCalendar;

	/**
	 * Instantiates a new jpa daily pattern get memento.
	 *
	 * @param patternCalendar the pattern calendar
	 */
	public JpaDailyPatternGetMemento(KdpstDailyPatternSet patternCalendar) {
		this.patternCalendar = patternCalendar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(patternCalendar.getKdpstDailyPatternSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
	 * getPatternCode()
	 */
	@Override
	public PatternCode getPatternCode() {
		return new PatternCode(patternCalendar.getKdpstDailyPatternSetPK().getPatternCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
	 * getPatternName()
	 */
	@Override
	public PatternName getPatternName() {
		return new PatternName(patternCalendar.getPatternName());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento#getListDailyPatternVal()
	 */
	@Override
	public List<DailyPatternVal> getListDailyPatternVal() {
		return this.patternCalendar.getListKdpstDailyPatternVal().stream()
				.map(entity -> new DailyPatternVal(new JpaDailyPatternValGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
