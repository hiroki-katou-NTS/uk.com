/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.role;

import java.util.List;
import java.util.Optional;

/**
 * The Class PersonInforRoleRepository
 * 
 * @author lanlt
 *
 */
public interface PersonInforRoleRepository {
	List<PersonInforRole> getAllPersonRole();

	Optional<PersonInforRole> getDetailPersonRole(String roleId, String companyId);

	void add(PersonInforRole domain);

	void update(PersonInforRole domain);

	void delete(String roleId);
}
