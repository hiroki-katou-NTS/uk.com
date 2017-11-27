package nts.uk.ctx.bs.employee.app.find.position.jobposition;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

public class SubJobPosParentFinder implements PeregSingleFinder<AffiDeptAndSubJobPos>{

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

	

}
