package repository.person.info.category;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

@Stateless
public class JpaPerInfoCategoryRepositoty extends JpaRepository implements PerInfoCategoryRepositoty {
	
	private final static String SELECT_QUERRY = "SELECT * FROM PpemtPerInfoCtg pc";
	
	@Override
	public List<PersonInfoCategory> getAllPerInfoCategory() {
		
		return null;
	}

	@Override
	public PersonInfoCategory getPerInfoCategory(String perInfoCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
