/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.query.kmk004.p;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.ExcessOutsideTimeSetReg;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@Builder
public class ExcessOutsideTimeSetRegDto {

	//定内残業を含める
	private boolean legalOverTimeWork;

	//法定外休出を含める
	private boolean legalHoliday;

	public static ExcessOutsideTimeSetRegDto from(ExcessOutsideTimeSetReg domain){
		
		return ExcessOutsideTimeSetRegDto.builder()
				.legalHoliday(domain.isLegalHoliday())
				.legalOverTimeWork(domain.isLegalOverTimeWork())
				.build();
	}
}
