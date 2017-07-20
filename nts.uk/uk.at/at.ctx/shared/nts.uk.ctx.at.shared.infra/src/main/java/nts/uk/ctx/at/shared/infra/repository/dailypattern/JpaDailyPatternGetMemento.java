/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetting;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSet;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDailyPatternGetMemento.
 */
public class JpaDailyPatternGetMemento implements DailyPatternGetMemento{

	  /** The pattern calendar. */
    private KdpstDailyPatternSet patternCalendar;
    
    /**
     * Instantiates a new jpa daily pattern get memento.
     *
     * @param patternCalendar the pattern calendar
     */
//    private KcvmtContCalendarVal patternCalendarVal;

	/**
	 * @param patternCalendar
	 */
	public JpaDailyPatternGetMemento(KdpstDailyPatternSet patternCalendar) {
		this.patternCalendar = patternCalendar;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return patternCalendar.getKcsmtContCalendarSetPK().getCid();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getPatternCode()
	 */
	@Override
	public String getPatternCode() {
		return patternCalendar.getKcsmtContCalendarSetPK().getPatternCd();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getPatternName()
	 */
	@Override
	public String getPatternName() {
		return patternCalendar.getPatternName();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getWorkTypeCodes()
	 */
	@Override
	public List<String> getWorkTypeCodes() {
		 return this.patternCalendar.getListContCalender().stream()
	                .filter(entity -> entity.getKcvmtContCalendarValPK().getPatternCd() == this.patternCalendar.getKcsmtContCalendarSetPK().getPatternCd())
	                .map(entity -> entity.getWorkTypeSetCd())
	                .collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getWorkHouseCodes()
	 */
	@Override
	public List<String> getWorkHouseCodes() {
		 return this.patternCalendar.getListContCalender().stream()
	                .filter(entity -> entity.getKcvmtContCalendarValPK().getPatternCd() == this.patternCalendar.getKcsmtContCalendarSetPK().getPatternCd())
	                .map(entity -> entity.getWorkingHoursCd())
	                .collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getCalendarSetting()
	 */
	@Override
	public DailyPatternSetting getCalendarSetting() {
		return null;
//		return new CalendarSetting(new JpaCalendarSettingGetMemento(this.patternCalendarVal));
	}
	
	
}
