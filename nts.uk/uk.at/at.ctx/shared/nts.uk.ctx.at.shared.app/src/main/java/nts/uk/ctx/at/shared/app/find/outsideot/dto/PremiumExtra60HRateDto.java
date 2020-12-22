/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;

/**
 * The Class PremiumExtra60HRateDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremiumExtra60HRateDto {

	/** The breakdown item no. */
	private Integer breakdownItemNo;
	
	/** The premium rate. */
	private Integer premiumRate;
	
	/** The overtime no. */
	private  Integer overtimeNo;
	
	public static PremiumExtra60HRateDto of(int breakDownNo, PremiumExtra60HRate domain) {
		
		return new PremiumExtra60HRateDto(breakDownNo, domain.getPremiumRate().v(), domain.getOvertimeNo().value);
	} 
	
	public PremiumExtra60HRate domain() {
		
		return new PremiumExtra60HRate(new PremiumRate(premiumRate), EnumAdaptor.valueOf(overtimeNo, OvertimeNo.class));
	}
}