package nts.uk.ctx.at.record.ws.monthly.nursingleave;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.ChildCareNusingLeaveFinder;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto.KDL051ProcessDto;

@Path("record/monthly/nursingleave")
@Produces("application/json")
public class KDL051ChangeId {
	/** The finder. */
	@Inject
	ChildCareNusingLeaveFinder childCareNusingLeaveFinder;
	
	@Path("changeId")
	@POST
	public KDL051ProcessDto getAnnualHoliDayDto(ChangeSIDParam param) {
		return this.childCareNusingLeaveFinder.changeEmployee(param.getEmployeeId());
	}
	
	@Data
	class ChangeSIDParam {
		private String employeeId;
	}
}
