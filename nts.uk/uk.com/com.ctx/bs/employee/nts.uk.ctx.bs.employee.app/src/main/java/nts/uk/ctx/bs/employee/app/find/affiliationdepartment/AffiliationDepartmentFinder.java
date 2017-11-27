/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.affiliationdepartment;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AffiliationDepartmentFinder implements PeregSingleFinder<AffiliationDepartmentDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00011";
	}

	@Override
	public Class<AffiliationDepartmentDto> dtoClass() {
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
