package find.familymember;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class FamilyMemberSingleFinder implements PeregSingleFinder<FamilyMemberDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00004";
	}

	@Override
	public Class<FamilyMemberDto> dtoClass() {
		return FamilyMemberDto.class;
	}

	@Override
	public PeregDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}


}
