/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.dailypatternval.find.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento;

@Getter
@Setter
public class DailyPatternValDto implements DailyPatternValSetMemento  {

	/** The cid. */
	private String cid;
	
    /** The pattern cd. */
    private String patternCode;
    
    /** The disp order. */
    private Integer dispOrder;
    
    /** The work type set cd. */
    private String workTypeSetCd;
    
    /** The working hours cd. */
    private String workingHoursCd;
    
    /** The days. */
    private Integer days;

	@Override
	public void setCompanyId(String setCompanyId) {
	}

	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCode = setPatternCode;
	}

	@Override
	public void setWorkTypeCodes(String setWorkTypeCodes) {
		this.workTypeSetCd = setWorkTypeCodes ;
	}

	@Override
	public void setWorkHouseCodes(String setWorkHouseCodes) {
		this.workingHoursCd = setWorkHouseCodes ;
	}
	
	@Override
	public void setDays(Integer setDays) {
		this.days = setDays;
	}
	
}
