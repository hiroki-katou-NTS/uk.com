/**
 * 
 */
package find.groupitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroupRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class PersonInfoItemGroupFinder {

	@Inject
	private PersonInfoItemGroupRepository personInfoItemGroupRepository;

	List<PersonInfoItemGroupDto> getAllPersonInfoGroup() {
		return this.personInfoItemGroupRepository.getAllPersonInfoItemGroup().stream()
				.map(item -> PersonInfoItemGroupDto.fromDomain(item)).collect(Collectors.toList());
	}
}
