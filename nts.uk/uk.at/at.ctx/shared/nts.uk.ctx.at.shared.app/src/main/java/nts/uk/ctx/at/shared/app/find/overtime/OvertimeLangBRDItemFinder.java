/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.overtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.overtime.dto.OvertimeLangBRDItemDto;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.language.OvertimeLangBRDItem;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.language.OvertimeLangBRDItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeLangBRDItemFinder.
 */
@Stateless
public class OvertimeLangBRDItemFinder {

	/** The repository. */
	@Inject
	private OvertimeLangBRDItemRepository repository;
	
	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OvertimeLangBRDItemDto> findAll(String languageId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OvertimeLangBRDItem> overtimeLanguage = this.repository.findAll(companyId, languageId);

		return overtimeLanguage.stream().map(domain -> {
			OvertimeLangBRDItemDto dto = new OvertimeLangBRDItemDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
