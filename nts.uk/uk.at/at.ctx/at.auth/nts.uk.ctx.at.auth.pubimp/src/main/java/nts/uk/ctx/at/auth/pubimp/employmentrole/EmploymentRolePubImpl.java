package nts.uk.ctx.at.auth.pubimp.employmentrole;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.auth.dom.employmentrole.DisabledSegment;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeRefRange;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRole;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.ctx.at.auth.dom.employmentrole.ScheduleEmployeeRef;
import nts.uk.ctx.at.auth.pub.employmentrole.EmploymentRoleExport;
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

	@Override
	public List<EmploymentRoleExport> getListEmploymentRole(String companyId) {
		List<EmploymentRoleExport> data = repository.getListEmploymentRole(companyId).stream()
				.map(c->toDto(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public Optional<EmploymentRoleExport> getEmploymentRoleById(String companyId, String roleId) {
		Optional<EmploymentRoleExport> data = repository.getEmploymentRoleById(companyId, roleId)
				.map(c->toDto(c));
		return data;
	}

	@Override
	public void addEmploymentRole(EmploymentRoleExport employmentRole) {
		EmploymentRole data = toDomain(employmentRole);
		repository.addEmploymentRole(data);
	}

	@Override
	public void updateEmploymentRole(EmploymentRoleExport employmentRole) {
		EmploymentRole data = toDomain(employmentRole);
		repository.updateEmploymentRole(data);
		
	}

	@Override
	public void deleteEmploymentRole(String companyId, String roleId) {
		repository.deleteEmploymentRole(companyId, roleId);
		
	}
	
	private EmploymentRole toDomain(EmploymentRoleExport entity) {
		return new EmploymentRole(
				entity.getCompanyId(),
				entity.getRoleId(),
				EnumAdaptor.valueOf(entity.getScheduleEmployeeRef(), ScheduleEmployeeRef.class),
				EnumAdaptor.valueOf(entity.getBookEmployeeRef(),EmployeeRefRange.class),
				EnumAdaptor.valueOf(entity.getEmployeeRefSpecAgent(),EmployeeRefRange.class),
				EnumAdaptor.valueOf(entity.getPresentInqEmployeeRef(),EmployeeReferenceRange.class),
				EnumAdaptor.valueOf(entity.getFutureDateRefPermit(),DisabledSegment.class)
				);
	}
	
	private EmploymentRoleExport toDto(EmploymentRole domain ){
		return new EmploymentRoleExport(
				domain.getCompanyId(),
				domain.getRoleId(),
				domain.getScheduleEmployeeRef().value,
				domain.getBookEmployeeRef().value,
				domain.getEmployeeRefSpecAgent().value,
				domain.getPresentInqEmployeeRef().value,
				domain.getFutureDateRefPermit().value
				);
		
	}

}
