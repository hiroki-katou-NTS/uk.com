package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import java.util.List;
import java.util.Optional;

public interface PersonInfoCategoryAuthRepository {

	List<PersonInfoCategoryDetail> getAllCategory(String roleId, String contractCd, String companyId);

	void add(PersonInfoCategoryAuth domain);

	void update(PersonInfoCategoryAuth domain);

	void delete(String roleId, String personCategoryAuthId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId);
}
