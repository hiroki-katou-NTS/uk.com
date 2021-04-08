package nts.uk.ctx.sys.gateway.dom.securitypolicy.password.changelog;

import java.util.Optional;

public interface PasswordChangeLogRepository {
	Optional<PasswordChangeLog> find(String userId);
}
