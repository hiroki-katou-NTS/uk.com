package find.familymember;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregListFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class FamilyMemberListFinder implements PeregListFinder<FamilyMemberDto>{

	@Override
	public String targetCategoryCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<FamilyMemberDto> dtoClass() {
		return FamilyMemberDto.class;
	}

	@Override
	public List<PeregDto> getCtgListleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
