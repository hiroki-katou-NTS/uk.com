package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import java.util.List;
import java.util.Optional;

public interface PersonInfoCategoryAuthRepository {
	List<PersonInfoCategoryAuth> getAllPersonCategoryAuth();

	List<PersonInfoCategoryAuth> getAllPersonCategoryAuthByRoleId(String roleId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuth(String roleId,
			String personCategoryAuthId);

	void add(PersonInfoCategoryAuth domain);

	void update(PersonInfoCategoryAuth domain);

	void delete(String roleId,String personCategoryAuthId);
}
