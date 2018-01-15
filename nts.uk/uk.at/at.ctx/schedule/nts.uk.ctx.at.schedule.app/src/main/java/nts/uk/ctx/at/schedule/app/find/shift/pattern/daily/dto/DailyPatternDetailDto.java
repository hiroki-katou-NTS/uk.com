/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternVal;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.PatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.PatternName;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@Getter
@Setter
public class DailyPatternDetailDto implements DailyPatternSetMemento {

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternValDto> dailyPatternVals;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setCompanyId
	 * (java.lang.String)
	 */
	@Override
	public void setCompanyId(CompanyId setCompanyId) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(PatternCode patternCode) {
		this.patternCode = patternCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(PatternName patternName) {
		this.patternName = patternName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setWorkTypeCodes(java.util.List)
	 */
	@Override
	public void setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal) {
		this.dailyPatternVals = setListDailyPatternVal.stream()
				.map(item -> new DailyPatternValDto(item.getDispOrder().v(),
						item.getWorkTypeSetCd().v(), item.getWorkingHoursCd().v(),
						item.getDays().v()))
				.collect(Collectors.toList());
	}

}