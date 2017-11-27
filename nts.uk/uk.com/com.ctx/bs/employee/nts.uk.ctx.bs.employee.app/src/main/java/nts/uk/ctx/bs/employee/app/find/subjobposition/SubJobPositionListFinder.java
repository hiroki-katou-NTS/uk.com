/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.subjobposition;

import java.util.List;

import nts.uk.shr.pereg.app.find.PeregListFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
public class SubJobPositionListFinder implements PeregListFinder{

	@Override
	public String targetCategoryCode() {
		return "CS00012";
	}

	@Override
	public Class<?> dtoClass() {
		return SubJobPositionDto.class;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregListFinder#getCtgListleData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public List<PeregDto> getCtgListleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
