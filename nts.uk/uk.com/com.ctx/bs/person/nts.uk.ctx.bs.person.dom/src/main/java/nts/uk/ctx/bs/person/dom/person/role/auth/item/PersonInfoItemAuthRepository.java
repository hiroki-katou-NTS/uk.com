package nts.uk.ctx.bs.person.dom.person.role.auth.item;

import java.util.List;
import java.util.Optional;

public interface PersonInfoItemAuthRepository {
	List<PersonInfoItemAuth> getAllPersonItemAuth();

	List<PersonInfoItemAuth> getAllPersonItemAuthByCategory(String roleId, String personCategoryAuthId);

	Optional<PersonInfoItemAuth> getDetailPersonItemAuth(String roleId, String personCategoryAuthId,
			String personItemDefId);

	void add(PersonInfoItemAuth domain);

	void update(PersonInfoItemAuth domain);

	void delete(String roleId,String personCategoryAuthId, String personItemDefId);
}
