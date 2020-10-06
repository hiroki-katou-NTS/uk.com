package nts.uk.screen.at.ws.ksm.ksm008.i;

import nts.uk.screen.at.app.ksm008.command.i.KsmIDeleteContinuousWorkingHoursCommand;
import nts.uk.screen.at.app.ksm008.command.i.KsmIDeleteContinuousWorkingHoursCommandHandler;
import nts.uk.screen.at.app.ksm008.query.i.WorkingHourListDto;
import nts.uk.screen.at.app.ksm008.query.i.WorkingHourListScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/i")
@Produces("application/json")
public class ContinuousWorkHourSettingKsm008I {
    @Inject
    private WorkingHourListScreenQuery workingHourListScreenQuery;

    private KsmIDeleteContinuousWorkingHoursCommandHandler KsmIDeleteContinuousWorkingHoursCommandHandler;

   /*  @POST
    @Path("getStartupInfoCmp")
    public ConsecutiveWorkCmpDto getStartupInfoCmp() {
        return this.startupInfoCmpScreenQuery.get();
    }*/

    @POST
    @Path("getWorkingHourList")
    public WorkingHourListDto getWorkingHourList() {
        return workingHourListScreenQuery.get();
    }

    @DELETE
    @Path("deleteWorkHourSetting")
    public void deleteWorkHourSetting(KsmIDeleteContinuousWorkingHoursCommand command) {
        this.KsmIDeleteContinuousWorkingHoursCommandHandler.handle(command);
    }
}
