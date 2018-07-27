package nts.uk.ctx.pereg.dom.roles.auth;

import java.util.Optional;

/**
 * The Class PersonInfoRoleAuthReposotory
 * 
 * @author danpv
 *
 */
public interface PersonInfoRoleAuthRepository {

	Optional<PersonInfoRoleAuth> get(String roleId, String companyId);

}
