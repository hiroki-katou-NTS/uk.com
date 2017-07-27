package nts.uk.ctx.bs.person.dom.person.role.auth;

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

	Optional<PersonInfoRoleAuth> getDetailPersonRoleAuth(String roleId);

	void add(PersonInfoRoleAuth domain);

	void update(PersonInfoRoleAuth domain);

	void delete(String roleId);
}
