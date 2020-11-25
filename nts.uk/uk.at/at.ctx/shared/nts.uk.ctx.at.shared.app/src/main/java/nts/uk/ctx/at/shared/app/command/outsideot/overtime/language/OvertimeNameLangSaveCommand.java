/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.overtime.language;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLang;

/**
 * The Class OvertimeNameLangSaveCommand.
 */
@Getter
@Setter
public class OvertimeNameLangSaveCommand {

	/** The overtime languages. */
	List<OvertimeNameLangSaveDto> overtimeLanguages;
	
	/**
	 * To list domain.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OvertimeNameLang> toListDomain(String companyId){
		return this.overtimeLanguages.stream().map(dto -> dto.toDomain(companyId))
				.collect(Collectors.toList());
	}
}
