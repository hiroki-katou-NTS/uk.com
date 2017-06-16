/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyFinder.
 */
@Stateless
public class CompanyFinder {
	
	/** The repository. */
	@Inject
	private CompanyRepository repository;

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public CompanyDto getCompany() {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		Optional<Company> company = this.repository.getComanyId(companyId);
		
		CompanyDto dto = new CompanyDto();
		if(company.isPresent()){
			company.get().saveToMemento(dto);
		}
		return dto;
	}

}
