package nts.uk.ctx.sys.auth.pub.roleset;

import java.util.Optional;

public interface RoleSetPublisher {
	
	Optional<DefaultRoleSetDto> getDefault(String companyId);
}
