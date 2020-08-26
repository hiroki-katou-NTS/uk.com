package nts.uk.screen.at.ws.shift.workcycle;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCreateMethod;
import nts.uk.screen.at.app.shift.workcycle.BootMode;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDialog;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDto;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 勤務サイクル反映ダイアログWebservice
 * @author khai.dh
 */
@Path("screen/at/shift/workcycle/workcycle-reflection")
@Produces(MediaType.APPLICATION_JSON)
public class WorkCycleReflectionWebService extends WebService {
	@Inject private WorkCycleReflectionDialog wcrdScreenQuery;

    @POST
    @Path("start")
    public WorkCycleReflectionDto getStartupInfo(
			@FormParam("bootMode") BootMode bootMode,
			@FormParam("creationPeriodStartDate") String creationPeriodStartDate,
			@FormParam("creationPeriodEndDate") String creationPeriodEndDate,
			@FormParam("workCycleCode") String workCycleCode,
			@FormParam("refOrder") List<WorkCreateMethod> refOrder,
			@FormParam("numOfSlideDays") int numOfSlideDays){
		DatePeriod creationPeriod = createDatePeriod(creationPeriodStartDate, creationPeriodEndDate,"yyyy/MM/dd");
		WorkCycleReflectionDto dto = wcrdScreenQuery.getStartupInfo(
        		bootMode,
				creationPeriod,
				workCycleCode,
				refOrder,
				numOfSlideDays);

		return dto;
    }

	private DatePeriod createDatePeriod(String startDate, String endDate, String format){
		return new DatePeriod(GeneralDate.fromString(startDate,format), GeneralDate.fromString(endDate,format));
	}
}
