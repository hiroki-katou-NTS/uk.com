package nts.uk.ctx.pr.core.ws.wageprovision.wagetable;

import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableQualificationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.WageTableQualificationFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/wageprovision/wagetable")
@Produces("application/json")
public class WageTableQualificationWebService {
    @Inject
    private WageTableQualificationFinder wageTableQualificationFinder;

    @POST
    @Path("/get-wage-table-qualification/{historyId}/{isInitScreen}")
    public List<WageTableQualificationDto> getWageTableQualification(@PathParam("historyId") String historyId, @PathParam("isInitScreen") boolean isInitScreen) {
        return wageTableQualificationFinder.createWageTableQualification(historyId, isInitScreen);
    }
}