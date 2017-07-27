/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.dailypattern.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternCode;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternName;

@Getter
@Setter
public class DailyPatternDto implements DailyPatternSetMemento {

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternValDto> listDailyPatternVal;

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
	public void setPatternCode(PatternCode setPatternCode) {
		this.patternCode = setPatternCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(PatternName setPatternName) {
		this.patternName = setPatternName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#
	 * setWorkTypeCodes(java.util.List)
	 */
	@Override
	public void setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal) {
		List<DailyPatternValDto> listDailyPatternValDto = new ArrayList<>();
		setListDailyPatternVal.forEach(new Consumer<DailyPatternVal>() {
			public void accept(DailyPatternVal t) {
				DailyPatternValDto d = new DailyPatternValDto( t.getPatternCd().v(),
						t.getDispOrder().v(), t.getWorkTypeSetCd().v(), t.getWorkingHoursCd().v(), t.getDays().v());
				listDailyPatternValDto.add(d);
			};
		});
		this.listDailyPatternVal = listDailyPatternValDto;
	}

}