package nts.uk.ctx.bs.employee.app.find.position.jobposition;

import java.util.List;

import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

public class SubJobPosParentFinder implements PeregFinder<AffiDeptAndSubJobPos>{

	@Override
	public String targetCategoryCode() {
		return "CS00013";
	}

	@Override
	public Class<AffiDeptAndSubJobPos> dtoClass() {
		return AffiDeptAndSubJobPos.class;
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
