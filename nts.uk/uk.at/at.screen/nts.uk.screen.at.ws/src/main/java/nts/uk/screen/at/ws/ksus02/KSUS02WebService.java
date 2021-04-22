package nts.uk.screen.at.ws.ksus02;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.app.command.workrequest.SubmitWorkRequest;
import nts.uk.ctx.at.schedule.app.command.workrequest.SubmitWorkRequestCmd;
import nts.uk.screen.at.app.ksus02.FirstInformationDto;
import nts.uk.screen.at.app.ksus02.GetDisplayInforOuput;
import nts.uk.screen.at.app.ksus02.GetDisplayInformation;
import nts.uk.screen.at.app.ksus02.GetInforInitialStartup;
import nts.uk.shr.com.context.AppContexts;

@Path("screen/at/schedule")
@Produces("application/json")
public class KSUS02WebService extends WebService {
	@Inject
	private GetInforInitialStartup getInforInitialStartup;

	@Inject
	private SubmitWorkRequest submitWorkRequest;


	@Inject
	private GetDisplayInformation getDisplayInformation;
	@POST
	@Path("getInforinitialStartup")
	public FirstInformationDto get(Ksus02Input input) {
		FirstInformationDto data = getInforInitialStartup
				.get(GeneralDate.fromString(input.getBaseDate(), "yyyy/MM/dd"));
		return data;
	}

	@POST
	@Path("saveworkrequest")
	public void save(SubmitWorkRequestCmd command) {
		submitWorkRequest.handle(command);
	}

	@POST
	@Path("getWorkRequest")
	public GetDisplayInforOuput getWorkRequest(Ksus02InputPeriod period) {
		String employeeId = AppContexts.user().employeeId();
		List<String> listEmp = new ArrayList<>();
		listEmp.add(employeeId);
		GetDisplayInforOuput data = getDisplayInformation.get(listEmp,
				new DatePeriod(GeneralDate.fromString(period.getStartDate(), "yyyy/MM/dd"),
						GeneralDate.fromString(period.getEndDate(), "yyyy/MM/dd")));
		return data;
	}


}
