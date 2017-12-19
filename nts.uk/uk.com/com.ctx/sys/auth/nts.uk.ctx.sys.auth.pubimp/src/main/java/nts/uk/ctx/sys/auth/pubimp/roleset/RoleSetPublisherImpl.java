package nts.uk.ctx.sys.auth.pubimp.roleset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.pub.roleset.DefaultRoleSetDto;
import nts.uk.ctx.sys.auth.pub.roleset.RoleSetPublisher;

@Stateless
public class RoleSetPublisherImpl implements RoleSetPublisher {

	@Inject
	private DefaultRoleSetRepository defaultRoleSetRepo;
	
	@Override
	public Optional<DefaultRoleSetDto> getDefault(String companyId) {
		return defaultRoleSetRepo.findByCompanyId(companyId)
				.map(r -> new DefaultRoleSetDto(r.getCompanyId(), r.getRoleSetCd().v()));
	}

}
