/**
 * 
 */
package find.currentaddress;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class CurrentAddressFinder implements PeregFinder<CurrentAddressDto>{

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
