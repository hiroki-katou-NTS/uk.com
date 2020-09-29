/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.overtime.language;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.OvertimeNameLangDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeLangNameFinder.
 */
@Stateless
public class OvertimeNameLangFinder {

	/** The repository. */
	@Inject
	private OvertimeNameLangRepository repository;
	
	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OvertimeNameLangDto> findAll(String languageId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OvertimeNameLang> overtimeLanguage = this.repository.findAll(companyId, languageId);

		return overtimeLanguage.stream().map(domain -> {
			OvertimeNameLangDto dto = new OvertimeNameLangDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
