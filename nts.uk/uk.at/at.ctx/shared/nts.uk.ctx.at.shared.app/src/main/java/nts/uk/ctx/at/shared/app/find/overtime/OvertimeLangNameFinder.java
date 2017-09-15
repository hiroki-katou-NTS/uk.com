/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeLangNameDto;
import nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangName;
import nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeLangNameFinder.
 */
@Stateless
public class OvertimeLangNameFinder {

	/** The repository. */
	@Inject
	private OvertimeLangNameRepository repository;
	
	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OvertimeLangNameDto> findAll(String languageId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OvertimeLangName> overtimeLanguage = this.repository.findAll(companyId, languageId);

		return overtimeLanguage.stream().map(domain -> {
			OvertimeLangNameDto dto = new OvertimeLangNameDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
