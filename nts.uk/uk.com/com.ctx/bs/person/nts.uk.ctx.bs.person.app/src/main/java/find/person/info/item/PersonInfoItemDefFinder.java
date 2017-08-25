package find.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

/**
 * The class PersonInfoItemDefFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoItemDefFinder {
	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	public List<PersonInfoItemDefDto> getAllPerInfoItemDefByCtgId(String perInfoCtgId) {
		return this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryId(perInfoCtgId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).stream()
				.map(item -> PersonInfoItemDefDto.fromDomain(item))
				.collect(Collectors.toList());
	};


}
