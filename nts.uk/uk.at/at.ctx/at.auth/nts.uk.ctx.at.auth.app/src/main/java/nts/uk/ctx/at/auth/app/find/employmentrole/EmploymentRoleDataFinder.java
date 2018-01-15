package nts.uk.ctx.at.auth.app.find.employmentrole;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.auth.app.find.employmentrole.dto.EmploymentRoleDataDto;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmploymentRoleDataFinder {
	
	@Inject
	private EmploymentRoleRepository repo;
	
	public List<EmploymentRoleDataDto> getListEmploymentRole() {
		String companyID = AppContexts.user().companyId();
		List<EmploymentRoleDataDto> data = repo.getListEmploymentRole(companyID).stream()
				.map(c-> EmploymentRoleDataDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	public EmploymentRoleDataDto getEmploymentRoleById( String roleId) {
		String companyID = AppContexts.user().companyId();
		Optional<EmploymentRoleDataDto> data = repo.getEmploymentRoleById(companyID, roleId).map(c->EmploymentRoleDataDto.fromDomain(c));
		if(data.isPresent())
			return data.get();
		return null;
	}
	
}