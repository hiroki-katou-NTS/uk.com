package nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionCategoryDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionCategoryFinder;

@Path("at/schedule/shift/shiftCondition/shiftCondition")
@Produces("application/json")
public class ShiftConditionCategoryWebService {
	@Inject
	private ShiftConditionCategoryFinder categoryFinder;

	@POST
	@Path("getAllShiftConCategory")
	public List<ShiftConditionCategoryDto> getAllListShiftConCategory() {
		return this.categoryFinder.getAllShiftConditonCategory();
	}
}
