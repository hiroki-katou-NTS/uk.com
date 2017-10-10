package nts.uk.ctx.bs.person.dom.person.role.auth.category;

import java.util.List;
import java.util.Optional;

public interface PersonInfoCategoryAuthRepository {

	List<PersonInfoCategoryDetail> getAllCategory(String roleId, String contractCd, String companyId);

	void add(PersonInfoCategoryAuth domain);

	void update(PersonInfoCategoryAuth domain);

	void delete(String roleId, String personCategoryAuthId);

	void deleteByRoleId(String roleId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId);

	List<PersonInfoCategoryAuth> getAllCategoryAuthByRoleId(String roleId);

	List<PersonInfoCategoryDetail> getAllCategoryInfo();

	List<PersonInfoCategoryDetail> getAllCategoryByRoleId(String roleId);

	List<PersonInfoCategoryDetail> getAllCategoryByCtgIdList(String contractCd, List<String> perInfoCtgIdlst);

}
