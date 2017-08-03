package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface PersonInfoCategoryAuthRepository {
	List<PersonInfoCategoryAuth> getAllPersonCategoryAuth();

	List<PersonInfoCategoryAuth> getAllPersonCategoryAuthByRoleId(String roleId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuth(String roleId,
			String personCategoryAuthId);
	List<PersonInfoCategoryDetail> getAllCategory(String roleId);

	void add(PersonInfoCategoryAuth domain);

	void update(PersonInfoCategoryAuth domain);

	void delete(String roleId,String personCategoryAuthId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuthByPId(String personCategoryAuthId);
}
