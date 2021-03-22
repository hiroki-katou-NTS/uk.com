package nts.uk.screen.at.ws.ksus02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.schedule.app.command.workrequest.SubmitWorkRequest;
import nts.uk.ctx.at.schedule.app.command.workrequest.SubmitWorkRequestCmd;
import nts.uk.ctx.at.schedule.app.query.workrequest.GetWorkRequestByEmpsAndPeriod;
import nts.uk.ctx.at.schedule.app.query.workrequest.WorkAvailabilityDisplayInfoOfOneDayDto;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityDisplayInfoDto;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityOfOneDayDto;
import nts.uk.screen.at.app.ksus02.FirstInformationDto;
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
	private GetWorkRequestByEmpsAndPeriod getWorkRequestByEmpsAndPeriod;

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
	public List<WorkAvailabilityOfOneDayDto> getWorkRequest(Ksus02InputPeriod period) {
		String employeeId = AppContexts.user().employeeId();
		List<String> listEmp = new ArrayList<>();
		listEmp.add(employeeId);
		List<WorkAvailabilityDisplayInfoOfOneDayDto> data = getWorkRequestByEmpsAndPeriod.get(listEmp,
				new DatePeriod(GeneralDate.fromString(period.getStartDate(), "yyyy/MM/dd"),
						GeneralDate.fromString(period.getEndDate(), "yyyy/MM/dd")));
		return data.stream().map(c -> convertToWorkAvailabilityDisplayInfoOfOneDayDto(c)).collect(Collectors.toList());
	}

	private WorkAvailabilityOfOneDayDto convertToWorkAvailabilityDisplayInfoOfOneDayDto(
			WorkAvailabilityDisplayInfoOfOneDayDto dto) {
		return new WorkAvailabilityOfOneDayDto(dto.getEmployeeId(), dto.getAvailabilityDate(), dto.getMemo(),
				new WorkAvailabilityDisplayInfoDto(dto.getDisplayInfo().getAssignmentMethod(),
						dto.getDisplayInfo().getNameList(),
						dto.getDisplayInfo().getTimeZoneList().stream()
								.map(c -> new TimeSpanForCalcDto(c.getStartTime(), c.getEndTime()))
								.collect(Collectors.toList())));
	}

}
