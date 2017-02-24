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
import nts.uk.ctx.pr.core.app.wagetable.find.dto.CertificationFindInDto;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class CertificationFinder.
 */
@Stateless
@Transactional
public class CertificationFinder {

	/** The find. */
	@Inject
	CertificationReponsitory find;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	public List<CertificationFindInDto> findAll() {
		List<CertificationFindInDto> lstCertificationFindIn = new ArrayList<>();
		for (Certification certification : find.findAll(new CompanyCode(AppContexts.user().companyCode()))) {
			CertificationFindInDto certificationFindInDto = new CertificationFindInDto();
			certification.saveToMemento(certificationFindInDto);
			lstCertificationFindIn.add(certificationFindInDto);
		}
		return lstCertificationFindIn;
	}

}
