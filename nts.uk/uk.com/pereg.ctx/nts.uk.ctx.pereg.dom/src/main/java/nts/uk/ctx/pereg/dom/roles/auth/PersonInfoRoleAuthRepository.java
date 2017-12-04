package nts.uk.ctx.pereg.dom.roles.auth;

import java.util.List;
import java.util.Optional;

/**
 * The Class PersonInfoRoleAuthReposotory
 * 
 * @author lanlt
 *
 */
public interface PersonInfoRoleAuthRepository {
	List<PersonInfoRoleAuth> getAllPersonInfoRoleAuth();

	Optional<PersonInfoRoleAuth> getDetailPersonRoleAuth(String roleId, String companyId);

	void add(PersonInfoRoleAuth domain);

	void update(PersonInfoRoleAuth domain);

	void delete(String roleId);
}
