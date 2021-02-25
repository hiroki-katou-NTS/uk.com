/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.breakdown.language;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLang;

/**
 * The Class OutsideOTBRDItemLangSaveCommand.
 */
@Getter
@Setter
public class OutsideOTBRDItemLangSaveCommand {

	/** The overtime languages. */
	List<OutsideOTBRDItemLangSaveDto> overtimeLanguages;
	
	/**
	 * To list domain.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutsideOTBRDItemLang> toListDomain(String companyId){
		return this.overtimeLanguages.stream().map(dto -> dto.toDomain(companyId))
				.collect(Collectors.toList());
	}
}
