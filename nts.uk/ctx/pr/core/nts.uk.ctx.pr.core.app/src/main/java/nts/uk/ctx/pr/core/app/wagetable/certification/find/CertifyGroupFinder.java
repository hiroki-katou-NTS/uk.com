/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.certification.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.wagetable.certification.find.dto.CertifyGroupFindDto;
import nts.uk.ctx.pr.core.app.wagetable.certification.find.dto.CertifyGroupFindOutDto;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CertifyGroupFinder.
 */
@Stateless
public class CertifyGroupFinder {

	/** The find. */
	@Inject
	private CertifyGroupRepository find;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<CertifyGroupFindOutDto> findAll() {

		// get info login
		LoginUserContext loginUserContext = AppContexts.user();

		// call findAll
		List<CertifyGroup> lstCertifyGroup = find.findAll(loginUserContext.companyCode());

		// to Dto
		return lstCertifyGroup.stream().map(certifyGroup -> {
			CertifyGroupFindOutDto certifyGroupFindOutDto = new CertifyGroupFindOutDto();
			certifyGroup.saveToMemento(certifyGroupFindOutDto);
			return certifyGroupFindOutDto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find.
	 *
	 * @param code
	 *            the code
	 * @return the certify group find dto
	 */
	public CertifyGroupFindDto find(String code) {
		
		// get info login
		LoginUserContext loginUserContext = AppContexts.user();
		CertifyGroupFindDto certifyGroupFindDto = new CertifyGroupFindDto();
		
		// call findById
		Optional<CertifyGroup> optionalCertifyGroup = find.findById(loginUserContext.companyCode(), code);
		
		// not value find
		if (!optionalCertifyGroup.isPresent()) {
			return null;
		}
		
		// to Dto
		optionalCertifyGroup.get().saveToMemento(certifyGroupFindDto);

		return certifyGroupFindDto;
	}

}
