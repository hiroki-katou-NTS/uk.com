package nts.uk.ctx.bs.person.dom.person.role.auth.item;

import java.util.List;

public interface PersonInfoItemAuthRepository {

	List<PersonInfoItemDetail> getAllItemDetail(String roleId, String personCategoryAuthId);

	void add(PersonInfoItemAuth domain);

	void update(PersonInfoItemAuth domain);

	void delete(String roleId, String personCategoryAuthId, String personItemDefId);
}
