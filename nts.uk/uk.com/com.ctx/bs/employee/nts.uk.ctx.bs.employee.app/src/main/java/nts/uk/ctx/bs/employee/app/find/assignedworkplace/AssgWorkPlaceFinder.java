/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.assignedworkplace;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AssgWorkPlaceFinder implements PeregSingleFinder{

	@Override
	public String targetCategoryCode() {
		return "CS00010";
	}

	@Override
	public Class<?> dtoClass() {
		return AssgWorkPlaceDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregSingleFinder#getSingleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public PeregDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
