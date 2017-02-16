/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find;

import java.util.ArrayList;
import java.util.List;

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

	public List<CertifyGroupFindInDto> findAll(String companyCode) {
		List<CertifyGroup> lstCertifyGroup = find.findAll(new CompanyCode(companyCode));
		List<CertifyGroupFindInDto> lstCertifyGroupFindInDto = new ArrayList<>();
		for (CertifyGroup certifyGroup : lstCertifyGroup) {
			CertifyGroupFindInDto certifyGroupFindInDto = new CertifyGroupFindInDto();
			certifyGroup.saveToMemento(certifyGroupFindInDto);
			lstCertifyGroupFindInDto.add(certifyGroupFindInDto);
		}
		return lstCertifyGroupFindInDto;
	}
	
	
}
