package nts.uk.ctx.pereg.dom.roles.auth.category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonInfoCategoryAuthRepository {

	List<PersonInfoCategoryDetail> getAllCategory(String roleId, String contractCd, String companyId);

	void add(PersonInfoCategoryAuth domain);

	void update(PersonInfoCategoryAuth domain);

	void delete(String roleId, String personCategoryAuthId);

	void deleteByRoleId(String roleId);

	Optional<PersonInfoCategoryAuth> getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId);
	
	Map<String, PersonInfoCategoryAuth> getByRoleIdAndCategories(String roleId, List<String> categoryIdList);
	
	Map<String, PersonInfoCategoryAuth> getByRoleId(String roleId);

	List<PersonInfoCategoryAuth> getAllCategoryAuthByRoleId(String roleId);

	List<PersonInfoCategoryDetail> getAllCategoryInfo();

	List<PersonInfoCategoryDetail> getAllCategoryByRoleId(String roleId);

	List<PersonInfoCategoryDetail> getAllCategoryByCtgIdList(String companyId, List<String> perInfoCtgIdlst);

}
