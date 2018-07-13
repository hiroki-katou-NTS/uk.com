package nts.uk.ctx.at.schedule.ws.shift.shiftcondition.shiftcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ItemConditionNames;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionDto;
import nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition.ShiftConditionFinder;

@Path("at/schedule/shift/shiftCondition/shiftCondition")
@Produces("application/json")
public class ShiftConditionWebService {
	@Inject
	private ShiftConditionFinder shiftConditionFinder;

	@POST
	@Path("getAllShiftCondition")
	public List<ShiftConditionDto> getListShiftCondition() {
		return this.shiftConditionFinder.getAllShiftCondition();
	}
	
	@POST
	@Path("checkExistedItems")
	public ItemConditionNames checkExistedItems(List<String> items){
		return shiftConditionFinder.checkExistedItems(items);
	}
}
