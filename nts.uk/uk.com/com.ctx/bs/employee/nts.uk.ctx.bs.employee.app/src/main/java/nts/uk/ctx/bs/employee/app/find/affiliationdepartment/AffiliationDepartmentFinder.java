/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.affiliationdepartment;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
public class AffiliationDepartmentFinder implements PeregSingleFinder{

	@Override
	public String targetCategoryCode() {
		return "CS00011";
	}

	@Override
	public Class<?> dtoClass() {
		return AffiliationDepartmentDto.class;
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
