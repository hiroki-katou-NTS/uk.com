package nts.uk.ctx.at.schedule.ws.shift.workcycle;


import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDto;
import nts.uk.screen.at.app.shift.workcycle.WorkCycleReflectionDialog;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

/**
 * 勤務サイクル反映ダイアログWebservice
 * @author khai.dh
 */
@Path("at/schedule/shift/workcycle/workcycle-reflection")
@Produces(MediaType.APPLICATION_JSON)
public class WorkCycleReflectionWebService extends WebService {
	@Inject private WorkCycleReflectionDialog wcrdScreenQuery;

    @POST
    @Path("start")
    public WorkCycleReflectionDto getStartupInfo(){
		LoginUserContext user = AppContexts.user();
		String companyId = user.companyId();
		// String employeeId = user.employeeId();
		String startMode = "";
		DatePeriod creationPeriod = null;
		Optional<String> workCycleCode = null;
        return wcrdScreenQuery.getStartupInfo(companyId, startMode, creationPeriod, workCycleCode);
    }
}
