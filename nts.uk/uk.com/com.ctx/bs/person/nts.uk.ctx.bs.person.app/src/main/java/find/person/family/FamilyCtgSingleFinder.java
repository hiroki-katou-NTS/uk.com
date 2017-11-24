package find.person.family;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregCtgSingleFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class FamilyCtgSingleFinder implements PeregCtgSingleFinder<FamilyCtgSingleFinder, PeregQuery>{

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
	public FamilyCtgSingleFinder getCtgSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
