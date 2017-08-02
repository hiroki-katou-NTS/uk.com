package repository.person.info.item;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefinitionRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;

@Stateless
public class JpaPernfoItemDefinitionRepositoty extends JpaRepository implements PernfoItemDefinitionRepositoty{

	@Override
	public List<PersonInfoItemDefinition> getAllPerInfoItemDefByCategoryId(String perInfoCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonInfoItemDefinition getPerInfoItemDefById(String perInfoItemDefId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonInfoItemDefinition> getPerInfoItemDefByListId(List<String> perInfoItemDefIds) {
		// TODO Auto-generated method stub
		return null;
	}

}
