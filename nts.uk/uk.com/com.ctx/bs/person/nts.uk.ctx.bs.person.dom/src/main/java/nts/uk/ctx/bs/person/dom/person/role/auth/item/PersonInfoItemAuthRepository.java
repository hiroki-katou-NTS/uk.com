package nts.uk.ctx.bs.person.dom.person.role.auth.item;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.info.setting.copysetting.EmpCopySettingItem;

public interface PersonInfoItemAuthRepository {

	List<PersonInfoItemDetail> getAllItemDetail(String roleId, String personInfoCategoryAuthId,String contractCd);
	
	List<PersonInfoItemAuth> getAllItemAuth(String roleId, String categoryId);

	Optional<PersonInfoItemAuth> getItemDetai(String roleId, String categoryId, String perInfoItemDefId);

	void add(PersonInfoItemAuth domain);

	void update(PersonInfoItemAuth domain);

	void delete(String roleId, String personCategoryAuthId, String personItemDefId);
	
	void deleteByRoleId(String roleId);
	
	
}
