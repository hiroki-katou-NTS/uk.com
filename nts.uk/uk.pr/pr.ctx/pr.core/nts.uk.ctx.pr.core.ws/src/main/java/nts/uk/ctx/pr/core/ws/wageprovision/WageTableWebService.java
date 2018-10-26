package nts.uk.ctx.pr.core.ws.wageprovision;

import nts.uk.ctx.pr.core.app.command.wageprovision.wagetable.WageTableHistoryCommand;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class WageTableWebService {

    @POST
    @Path("/addWageTable")
    public void addWageTable(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/updateWageTable")
    public void updateWageTable(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/addWageTableHistory")
    public void addWageTableHistory(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/editHistory")
    public void editWageTableHistory(WageTableHistoryCommand command) {

    }

    @POST
    @Path("/deleteHistory")
    public void deleteWageTableHistory(WageTableHistoryCommand command) {

    }
}
