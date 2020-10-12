package nts.uk.screen.at.ws.ksm.ksm008.i;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksm008.ConsecutiveAttendanceCom.ConsecutiveAttendanceComDto;
import nts.uk.screen.at.app.ksm008.command.i.*;
import nts.uk.screen.at.app.ksm008.query.i.Ksm008IStartInfoDto;
import nts.uk.screen.at.app.ksm008.query.i.Ksm008IStartupInfoScreenQuery;
import nts.uk.screen.at.app.ksm008.query.i.WorkingHourListDto;
import nts.uk.screen.at.app.ksm008.query.i.WorkingHourListScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/i")
@Produces("application/json")
public class ContinuousWorkHourSettingKsm008I extends WebService {
    @Inject
    private WorkingHourListScreenQuery workingHourListScreenQuery;

    @Inject
    private Ksm008IDeleteCommandHandler Ksm008IDeleteCommandHandler;

    @Inject
    private Ksm008IStartupInfoScreenQuery ksm008IStartupInfoScreenQuery;

    @Inject
    private Ksm008ICreateCommandHandler ksm008ICreateCommandHandler;

    @Inject
    private Ksm008IUpdateCommandHandler ksm008IUpdateCommandHandler;

    @POST
    @Path("getStartupInfo/{code}")
    public Ksm008IStartInfoDto getStartupInfo(@PathParam("code") String code) {
        ConsecutiveAttendanceComDto consecutiveAttendanceComDto = ksm008IStartupInfoScreenQuery.getStartupInfoCom(code);
        WorkingHourListDto workingHourListDto = workingHourListScreenQuery.get(code);
        return new Ksm008IStartInfoDto(consecutiveAttendanceComDto, workingHourListDto);
    }

    @POST
    @Path("getWorkingHourList/{code}")
    public WorkingHourListDto getWorkingHourList(@PathParam("code") String code) {
        return workingHourListScreenQuery.get(code);
    }

    @POST
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(Ksm008IDeleteCommand command) {
        Ksm008IDeleteCommandHandler.handle(command);
    }

    @POST
    @Path("createWorkHourSetting")
    public void createWorkHourSetting(Ksm008ICreateCommand command) {
        ksm008ICreateCommandHandler.handle(command);
    }

    @POST
    @Path("updateWorkHourSetting")
    public void updateWorkHourSetting(Ksm008IUpdateCommand command) {
        ksm008IUpdateCommandHandler.handle(command);
    }
}
