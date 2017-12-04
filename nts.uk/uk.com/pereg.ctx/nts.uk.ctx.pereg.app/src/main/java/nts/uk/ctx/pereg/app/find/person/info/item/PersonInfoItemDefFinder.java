package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.shr.com.context.AppContexts;

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
	
	public List<PersonInfoItemDefDto> getAllPerInfoItemDefByCategoryIdWithoutSetItem(String perInfoCtgId) {
		String contracCd = AppContexts.user().companyId().substring(0, 12);
		return this.pernfoItemDefRep
				.getAllPerInfoItemDefByCategoryIdWithoutSetItem(perInfoCtgId, contracCd).stream()
				.map(item -> PersonInfoItemDefDto.fromDomain(item))
				.collect(Collectors.toList());
	};
	
	
}
