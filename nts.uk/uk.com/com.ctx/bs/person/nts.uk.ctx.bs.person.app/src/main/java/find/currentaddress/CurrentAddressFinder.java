/**
 * 
 */
package find.currentaddress;

import nts.uk.shr.pereg.app.find.PeregCtgSingleFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
public class CurrentAddressFinder implements PeregCtgSingleFinder{

	@Override
	public String targetCategoryCode() {
		return "CS00003";
	}

	@Override
	public Class<CurrentAddressDto> dtoClass() {
		return CurrentAddressDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregCtgSingleFinder#getCtgSingleData(java.lang.Object)
	 */
	@Override
	public PeregDto getCtgSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
