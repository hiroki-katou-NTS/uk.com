/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.CertifyGroupDto;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupRepository;

@Stateless
@Transactional
public class CertifyGroupFinder {

	/** The find. */
	@Inject
	private CertifyGroupRepository find;

	public List<CertifyGroupFindOutDto> findAll(String companyCode) {
		List<CertifyGroup> lstCertifyGroup = find.findAll(new CompanyCode(companyCode));
		List<CertifyGroupFindOutDto> lstCertifyGroupFindInDto = new ArrayList<>();
		for (CertifyGroup certifyGroup : lstCertifyGroup) {
			CertifyGroupFindOutDto certifyGroupFindOutDto = new CertifyGroupFindOutDto();
			certifyGroup.saveToMemento(certifyGroupFindOutDto);
			lstCertifyGroupFindInDto.add(certifyGroupFindOutDto);
		}
		return lstCertifyGroupFindInDto;
	}

	public CertifyGroupDto find(CertifyGroupFindInDto certifyGroupFindInDto) {
		CertifyGroupDto certifyGroupDto = new CertifyGroupDto();
		Optional<CertifyGroup> optionalCertifyGroup = find
				.findById(new CompanyCode(certifyGroupFindInDto.getCompanyCode()), certifyGroupFindInDto.getCode());
		if (optionalCertifyGroup.isPresent()) {
			optionalCertifyGroup.get().saveToMemento(certifyGroupDto);
			return certifyGroupDto;
		}
		return null;

	}

}
