/**
 * 
 */
package find.groupitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.groupitem.IPersonInfoItemGroupRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class PersonInfoItemGroupFinder {

	@Inject
	private IPersonInfoItemGroupRepository repo;

	List<PersonInfoItemGroupDto> getAllPersonInfoGroup() {
		return this.repo.getAll().stream()
				.map(item -> PersonInfoItemGroupDto.fromDomain(item)).collect(Collectors.toList());
	}
}
