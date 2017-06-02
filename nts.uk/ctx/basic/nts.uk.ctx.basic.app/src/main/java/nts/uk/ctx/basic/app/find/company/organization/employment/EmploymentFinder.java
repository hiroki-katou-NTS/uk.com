package nts.uk.ctx.basic.app.find.company.organization.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.employment.dto.EmploymentFindDto;
import nts.uk.ctx.basic.dom.company.organization.employment.Employment;
import nts.uk.ctx.basic.dom.company.organization.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class EmploymentFinder {

	@Inject
	private EmploymentRepository repository;
	
	public List<EmploymentFindDto> findAll() {
		
		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// Get Company Id
		String companyId = loginUserContext.companyId();
		
		// Get All Employment
		List<Employment> empList = this.repository.findAll(companyId);
		
		// Save to Memento
		return empList.stream().map(empoyment -> {
			EmploymentFindDto dto = new EmploymentFindDto();
			empoyment.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
