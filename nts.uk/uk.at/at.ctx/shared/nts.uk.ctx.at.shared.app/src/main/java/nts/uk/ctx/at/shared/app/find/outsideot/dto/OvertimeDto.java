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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeValue;

/**
 * The Class OvertimeDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeDto {
	
	/** The name. */
	private String name;
	
	/** The overtime. */
	private Integer overtime;
	
	/** The overtime no. */
	private Integer overtimeNo;
	
	/** The use classification. */
	private Boolean useClassification;
	
	/** The super holiday 60 H occurs. */
	private Boolean superHoliday60HOccurs;

	public static OvertimeDto of(Overtime domain) {
		
		return new OvertimeDto(domain.getName().v(), domain.getOvertime().v(), 
								domain.getOvertimeNo().value, domain.isUseClass(),
								domain.isSuperHoliday60HOccurs());
	}
	
	public Overtime domain() {
		
		return new Overtime(superHoliday60HOccurs,
				useClassification ? UseClassification.UseClass_Use : UseClassification.UseClass_NotUse,
				new OvertimeName(name), new OvertimeValue(overtime), 
				EnumAdaptor.valueOf(overtimeNo, OvertimeNo.class));
	}
	
}
