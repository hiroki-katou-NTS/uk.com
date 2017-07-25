/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.dailypattern.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetting;

// TODO: Auto-generated Javadoc

/**
 * Gets the pattern calendar number day.
 *
 * @return the pattern calendar number day
 */
@Getter

/**
 * Sets the pattern calendar number day.
 *
 * @param patternCalendarNumberDay the new pattern calendar number day
 */
@Setter

/**
 * The Class DailyPatternDto.
 */
public class DailyPatternDto implements DailyPatternSetMemento {
	
//	private String companyId;
	
	/** The pattern code. */
private String patternCode;
	
	/** The pattern name. */
	private String patternName;
	
	/** The work type codes. */
	private List<String> workTypeCodes;
	
	/** The work house codes. */
	private List<String> workHouseCodes;
	
	/** The pattern calendar number day. */
	private Integer patternCalendarNumberDay;


	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String setCompanyId) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCode = setPatternCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(String setPatternName) {
		this.patternName = setPatternName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setWorkTypeCodes(java.util.List)
	 */
	@Override
	public void setWorkTypeCodes(List<String> setWorkTypeCodes) {
		this.workTypeCodes = setWorkTypeCodes;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setWorkHouseCodes(java.util.List)
	 */
	@Override
	public void setWorkHouseCodes(List<String> setWorkHouseCodes) {
		this.workHouseCodes = setWorkHouseCodes;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setCalendarSetting(nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetting)
	 */
	@Override
	public void setCalendarSetting(DailyPatternSetting setCalendarSetting) {
			if (setCalendarSetting.getNumberDayCalendar() != null) {
	            this.patternCalendarNumberDay = setCalendarSetting.getNumberDayCalendar().v();
	        }
	}


	/* 選択使用設定 */
	// private InputSet selectSet;
	// /*入力使用設定*/
	// private InputSet inputSet;
//	public static PatternCalendarDto fromDomain(PatternCalendar domain) {
//		return null;
		// return new PatternCalendarSettingDto(
		// domain.getCompanyId(),
		// domain.getDivTimeId(),
		// domain.getDivTimeName().v(),
		// Integer.valueOf(domain.getDivTimeUseSet().value),
		// Integer.valueOf(domain.getAlarmTime().toString()),
		// Integer.valueOf(domain.getErrTime().toString()),
		//// InputSet.convertType(domain.getSelectSet().getSelectUseSet().value,domain.getSelectSet().getCancelErrSelReason().value),
		//// InputSet.convertType(domain.getInputSet().getSelectUseSet().value,
		// domain.getInputSet().getCancelErrSelReason().value)
		// );

//	}
}