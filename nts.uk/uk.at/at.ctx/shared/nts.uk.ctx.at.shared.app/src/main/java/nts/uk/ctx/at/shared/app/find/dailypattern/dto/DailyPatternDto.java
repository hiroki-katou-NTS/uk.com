/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.dailypattern.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;


@Getter
@Setter
public class DailyPatternDto implements DailyPatternSetMemento {
	
//	private String companyId;
	
	/** The pattern code. */
	private String patternCode;
	
	/** The pattern name. */
	private String patternName;
	
	/** The list daily pattern val. */
	private List<DailyPatternVal> listDailyPatternVal;
	


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
	public void setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal) {
		this.listDailyPatternVal = setListDailyPatternVal;
	}




	/* é�¸æŠžä½¿ç”¨è¨­å®š */
	// private InputSet selectSet;
	// /*å…¥åŠ›ä½¿ç”¨è¨­å®š*/
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