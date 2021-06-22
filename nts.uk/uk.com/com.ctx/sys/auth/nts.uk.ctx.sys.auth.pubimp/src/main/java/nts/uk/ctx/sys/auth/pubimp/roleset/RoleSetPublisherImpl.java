package nts.uk.ctx.sys.auth.pubimp.roleset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.pub.roleset.DefaultRoleSetDto;
import nts.uk.ctx.sys.auth.pub.roleset.RoleSetDto;
import nts.uk.ctx.sys.auth.pub.roleset.RoleSetPublisher;

@Stateless
public class RoleSetPublisherImpl implements RoleSetPublisher {

	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepo;
	
	@Inject
	private RoleSetRepository roleSetRepo;
	
	@Override
	public Optional<DefaultRoleSetDto> getDefault(String companyId) {
		return defaultRoleSetRepo.findByCompanyId(companyId)
				.map(r -> new DefaultRoleSetDto(r.getCompanyId(), r.getRoleSetCd().v()));
	}
	
	@Override
	public Optional<RoleSetDto> getRoleSet(String companyId, String roleSetCd) {
		return roleSetRepo.findByRoleSetCdAndCompanyId(roleSetCd, companyId)
				.map(s -> new RoleSetDto(s.getRoleSetCd().v(), s.getCompanyId(), s.getRoleSetName().v() 
						,	s.getApprovalAuthority().value
						,	s.getOfficeHelperRoleId().isPresent()? s.getOfficeHelperRoleId().get(): null
						,	s.getMyNumberRoleId().isPresent()? s.getMyNumberRoleId().get(): null
						,	s.getHRRoleId().isPresent()? s.getHRRoleId().get(): null
						,	s.getPersonInfRoleId().isPresent()? s.getPersonInfRoleId().get(): null
						,	s.getEmploymentRoleId().isPresent()? s.getEmploymentRoleId().get(): null
						,	s.getSalaryRoleId().isPresent()? s.getSalaryRoleId().get(): null));
	}

}
