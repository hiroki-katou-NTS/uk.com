package nts.uk.ctx.at.request.ws.dialog.annualholiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.AnnualHolidayFinder;
import nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto.AnnualHolidayDto;

@Path("at/request/dialog/annualholiday")
@Produces("application/json")
public class AnnualHolidayWebServices extends WebService {

	@Inject
	private AnnualHolidayFinder finder;

	@POST
	@Path("startPage")
	public AnnualHolidayDto startPage(StartPageParam param) {
		return this.finder.starPage(param.getBaseDate(), param.getEmployeeIds());
	}

	@POST
	@Path("changeID")
	public AnnualHolidayDto getAnnualHoliDayDto(ChangeSIDParam param) {
		return this.finder.getAnnualHoliDayDto(param.getEmployeeId(), param.getBaseDate());
	}

}

@Data
class StartPageParam {
	private GeneralDate baseDate;
	private List<String> employeeIds;
}

@Data
class ChangeSIDParam {
	private String employeeId;
	private GeneralDate baseDate;
}
