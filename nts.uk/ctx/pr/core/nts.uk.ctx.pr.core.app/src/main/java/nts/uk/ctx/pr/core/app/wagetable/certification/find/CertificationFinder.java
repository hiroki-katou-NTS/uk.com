/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.certification.find;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.wagetable.certification.find.dto.CertificationFindInDto;
import nts.uk.ctx.pr.core.dom.wagetable.certification.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.certification.CertificationReponsitory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CertificationFinder.
 */
@Stateless
public class CertificationFinder {

	/** The find. */
	@Inject
	CertificationReponsitory find;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<CertificationFindInDto> findAll() {
		// get info login
		LoginUserContext loginUserContext = AppContexts.user();
		//call findAll None Group
		List<Certification> lstCertification = find.findAllNoneOfGroup(new CompanyCode(loginUserContext.companyCode()));
		//to Dto
		List<CertificationFindInDto> lstCertificationFindIn = new ArrayList<>();
		for (Certification certification : lstCertification) {
			CertificationFindInDto certificationFindInDto = new CertificationFindInDto();
			certification.saveToMemento(certificationFindInDto);
			lstCertificationFindIn.add(certificationFindInDto);
		}
		return lstCertificationFindIn;
	}

}
