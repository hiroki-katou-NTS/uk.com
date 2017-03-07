/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.certification.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.certification.find.dto.CertifyGroupFindDto;
import nts.uk.ctx.pr.core.app.wagetable.certification.find.dto.CertifyGroupFindOutDto;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertifyGroupRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CertifyGroupFinder.
 */
@Stateless
@Transactional
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

		List<CertifyGroup> lstCertifyGroup = find
				.findAll(new CompanyCode(AppContexts.user().companyCode()));

		List<CertifyGroupFindOutDto> lstCertifyGroupFindInDto = new ArrayList<>();

		for (CertifyGroup certifyGroup : lstCertifyGroup) {
			CertifyGroupFindOutDto certifyGroupFindOutDto = new CertifyGroupFindOutDto();
			certifyGroup.saveToMemento(certifyGroupFindOutDto);
			lstCertifyGroupFindInDto.add(certifyGroupFindOutDto);
		}

		return lstCertifyGroupFindInDto;
	}

	/**
	 * Find.
	 *
	 * @param code
	 *            the code
	 * @return the certify group find dto
	 */
	public CertifyGroupFindDto find(String code) {
		CertifyGroupFindDto certifyGroupFindDto = new CertifyGroupFindDto();

		Optional<CertifyGroup> optionalCertifyGroup = find
				.findById(new CompanyCode(AppContexts.user().companyCode()), code);

		if (!optionalCertifyGroup.isPresent()) {
			return null;
		}

		optionalCertifyGroup.get().saveToMemento(certifyGroupFindDto);

		return certifyGroupFindDto;
	}

}
