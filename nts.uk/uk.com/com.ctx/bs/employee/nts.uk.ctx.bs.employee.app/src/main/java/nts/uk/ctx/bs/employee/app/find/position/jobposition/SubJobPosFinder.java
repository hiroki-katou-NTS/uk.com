package nts.uk.ctx.bs.employee.app.find.position.jobposition;

import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

public class SubJobPosFinder implements PeregSingleFinder<SubJobPositionDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00013SD";
	}

	@Override
	public Class<SubJobPositionDto> dtoClass() {
		return SubJobPositionDto.class;
	}

	@Override
	public PeregDto getSingleData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
