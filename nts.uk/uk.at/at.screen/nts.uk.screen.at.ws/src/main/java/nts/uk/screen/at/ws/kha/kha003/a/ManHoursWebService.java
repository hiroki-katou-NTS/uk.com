package nts.uk.screen.at.ws.kha.kha003.a;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.workmanagement.manhoursummarytable.*;
import nts.uk.screen.at.app.kha003.ManHourSummaryTableFormatDto;
import nts.uk.screen.at.app.kha003.ManHoursDto;
import nts.uk.screen.at.app.kha003.a.CdParam;
import nts.uk.screen.at.app.kha003.a.ManHourSummaryLayoutScreenQuery;
import nts.uk.screen.at.app.kha003.a.ManHoursListScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/screen/kha003/a")
@Produces("application/json")
public class ManHoursWebService extends WebService {
    @Inject
    private ManHoursListScreenQuery initScreen;

    @Inject
    private ManHourSummaryLayoutScreenQuery summaryLayoutInfo;

    @Inject
    private RegisterManHourSummaryTableCommandHandler register;

    @Inject
    private UpdateManHourSummaryTableCommandHandler update;

    @Inject
    private DeleteManHourSummaryTableCommandHandler delete;

    @POST
    @Path("init")
    public ManHoursDto getInitScreen() {
        return this.initScreen.getManHoursList();
    }

    @POST
    @Path("man-hour-summary-layout")
    public ManHourSummaryTableFormatDto getManHourSummaryLayout(CdParam param) {
        return this.summaryLayoutInfo.get(param.getCode());
    }

    @POST
    @Path("register")
    public void registerManHourSummaryTable(RegisterOrUpdateManHourSummaryTableCommand command) {
        this.register.handle(command);
    }

    @POST
    @Path("update")
    public void updateManHourSummaryTable(RegisterOrUpdateManHourSummaryTableCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("delete")
    public void deleteManHourSummaryTable(DeleteManHourSummaryTableCommand command) {
        this.delete.handle(command);
    }
}
