/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternCode;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternName;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;

/**
 * The Class JpaDailyPatternSetMemento.
 */
public class JpaDailyPatternSetMemento implements DailyPatternSetMemento {

	/** The pattern calendar. */
	private KdpstDailyPatternSet patternCalendar;

	/**
	 * @param patternCalendar
	 */
	public JpaDailyPatternSetMemento(KdpstDailyPatternSet patternCalendar) {
		// check exist primary key
		if (patternCalendar.getKdpstDailyPatternSetPK() == null) {
			patternCalendar.setKdpstDailyPatternSetPK(new KdpstDailyPatternSetPK());
		}
		this.patternCalendar = patternCalendar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.patternCalendar.getKdpstDailyPatternSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(PatternCode patternCode) {
		this.patternCalendar.getKdpstDailyPatternSetPK().setPatternCd(patternCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(PatternName patternName) {
		this.patternCalendar.setPatternName(patternName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setListDailyPatternVal(java.util.List)
	 */
	@Override
	public void setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal) {
		this.patternCalendar
				.setListKdpstDailyPatternVal(setListDailyPatternVal.stream().map(domain -> {
					KdpstDailyPatternVal entity = new KdpstDailyPatternVal();
					domain.saveToMemento(new JpaDailyPatternValSetMemento(entity));
					return entity;
				}).collect(Collectors.toList()));
	}

}
