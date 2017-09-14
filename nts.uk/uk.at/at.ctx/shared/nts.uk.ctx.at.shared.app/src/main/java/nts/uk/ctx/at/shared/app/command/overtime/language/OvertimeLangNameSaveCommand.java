/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.language;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangName;

@Getter
@Setter
public class OvertimeLangNameSaveCommand {

	/** The overtime languages. */
	List<OvertimeLangNameSaveDto> overtimeLanguages;
	
	/**
	 * To list domain.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OvertimeLangName> toListDomain(String companyId){
		return this.overtimeLanguages.stream().map(dto -> dto.toDomain(companyId))
				.collect(Collectors.toList());
	}
}
