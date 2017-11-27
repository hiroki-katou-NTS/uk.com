package find.person.family;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregCtgListFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class FamilyCtgListFinder implements PeregCtgListFinder<List<FamilyMemberDto>, PeregQuery>{

	@Override
	public String targetCategoryCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> dtoClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FamilyMemberDto> getCtgListleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
