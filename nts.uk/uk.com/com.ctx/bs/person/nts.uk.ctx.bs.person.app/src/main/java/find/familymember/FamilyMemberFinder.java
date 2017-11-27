package find.familymember;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class FamilyMemberFinder implements PeregFinder<FamilyMemberDto>{

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

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public List<PeregDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}


}
