/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.company.organization.employment.Employment;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.find.employment.EmploymentDto;
import nts.uk.shr.find.employment.EmploymentFinder;

/**
 * The Class DefaultEmploymentFinder.
 */
@Stateless
public class DefaultEmploymentFinder implements EmploymentFinder{

	/** The repository. */
	@Inject
	private EmploymentRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.uk.shr.find.employment.EmploymentFinder#findAll()
	 */
	public List<EmploymentDto> findAll() {

		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();

		// Get Company Id
		String companyId = loginUserContext.companyId();

		// Get All Employment
		List<Employment> empList = this.repository.findAll(companyId);

		// Save to Memento
		return empList.stream().map(empoyment -> {
			EmploymentDto dto = new EmploymentDto();
			dto.setCode(empoyment.getEmploymentCode().v());
			dto.setName(empoyment.getEmploymentName().v());
			return dto;
		}).collect(Collectors.toList());
	}
	
	

	/* (non-Javadoc)
	 * @see nts.uk.shr.find.employment.EmploymentFinder#findByCode(java.lang.String)
	 */
	@Override
	public EmploymentDto findByCode(String employmentCode) {
		String companyId = AppContexts.user().companyId();
		EmploymentDto dto = new EmploymentDto();
		Optional<Employment> employment = this.repository.findEmployment(companyId, employmentCode);
		if (employment.isPresent()) {
			dto.setCode(employmentCode);
			dto.setName(employment.get().toString());
			return dto;
		}
		return null;
	}
}
