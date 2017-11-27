package find.person.family;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregCtgSingleFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class FamilyCtgSingleFinder implements PeregCtgSingleFinder{

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
	public PeregDto getCtgSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}


}
