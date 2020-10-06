/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.outsideot.breakdown.language;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.outsideot.dto.OutsideOTBRDItemLangDto;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OutsideOTBRDItemLangFinder.
 */
@Stateless
public class OutsideOTBRDItemLangFinder {

	/** The repository. */
	@Inject
	private OutsideOTBRDItemLangRepository repository;
	
	/**
	 * Find all.
	 *
	 * @param languageId the language id
	 * @return the list
	 */
	public List<OutsideOTBRDItemLangDto> findAll(String languageId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		List<OutsideOTBRDItemLang> overtimeLanguage = this.repository.findAll(companyId, languageId);

		return overtimeLanguage.stream().map(domain -> {
			OutsideOTBRDItemLangDto dto = new OutsideOTBRDItemLangDto();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());

	}
}
