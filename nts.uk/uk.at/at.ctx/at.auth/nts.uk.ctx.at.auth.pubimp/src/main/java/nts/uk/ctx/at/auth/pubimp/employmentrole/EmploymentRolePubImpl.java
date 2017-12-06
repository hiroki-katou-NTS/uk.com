package nts.uk.ctx.at.auth.pubimp.employmentrole;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.ctx.at.auth.pub.employmentrole.EmploymentRolePub;
import nts.uk.ctx.at.auth.pub.employmentrole.EmploymentRolePubDto;

@Stateless
public class EmploymentRolePubImpl implements EmploymentRolePub {

	@Inject
	private EmploymentRoleRepository repository;

	@Override
	public List<EmploymentRolePubDto> getAllByCompanyId(String companyId) {
		return repository.getAllByCompanyId(companyId).stream().map(item -> {
			return new EmploymentRolePubDto(item.getCompanyId(), item.getRoleId());
		}).collect(Collectors.toList());
	}
}
