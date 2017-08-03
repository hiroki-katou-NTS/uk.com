package find.person.info.item;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;

@Stateless
public class PerInfoItemDefFinder {
	
	@Inject
	private PernfoItemDefRepositoty pernfoItemDefRepositoty;
	
	public List<PerInfoItemDefNewLayoutDto> getAllPerInfoItemDefByCategoryId(String perInfoCtgId){
		pernfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(perInfoCtgId);
		return null;
		
	};

	public Optional<PerInfoItemDefNewLayoutDto> getPerInfoItemDefById(String perInfoItemDefId){
		return null;
		
	};
	
	public List<PerInfoItemDefNewLayoutDto> getPerInfoItemDefByListId(List<String> listItemDefId){
		return null;
		
	};
}
