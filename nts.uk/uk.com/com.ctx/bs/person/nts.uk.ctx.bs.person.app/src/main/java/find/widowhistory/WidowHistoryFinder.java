/**
 * 
 */
package find.widowhistory;

import nts.uk.shr.pereg.app.find.PeregSingleFinder;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class WidowHistoryFinder implements PeregSingleFinder{

	@Override
	public String targetCategoryCode() {
		return "CS00014";
	}

	@Override
	public Class<?> dtoClass() {
		return WidowHistoryDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregCtgSingleFinder#getCtgSingleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public PeregDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
