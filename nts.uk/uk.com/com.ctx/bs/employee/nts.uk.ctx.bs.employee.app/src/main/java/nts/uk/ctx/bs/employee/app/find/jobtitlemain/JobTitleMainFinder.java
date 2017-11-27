/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.jobtitlemain;

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
public class JobTitleMainFinder implements PeregFinder<JobTitleMainDto>{

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

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.find.PeregQuery)
	 */
	@Override
	public List<PeregDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
