/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.jobtitlemain;

import javax.ejb.Stateless;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

/**
 * @author danpv
 *
 */
@Stateless
public class JobTitleMainFinder implements PeregSingleFinder<JobTitleMainDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00009";
	}

	@Override
	public Class<JobTitleMainDto> dtoClass() {
		return JobTitleMainDto.class;
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
