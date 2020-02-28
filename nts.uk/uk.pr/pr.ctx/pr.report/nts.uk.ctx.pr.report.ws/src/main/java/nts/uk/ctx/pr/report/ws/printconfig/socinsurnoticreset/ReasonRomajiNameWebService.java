package nts.uk.ctx.pr.report.ws.printconfig.socinsurnoticreset;

import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.ReasonRomajiNameCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset.ReasonRomajiNameCommandHandle;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.ReasonRomajiNameDto;
import nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset.ReasonRomajiNameFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("ctx/pr/file/reason/romaji")
@Produces("application/json")
public class ReasonRomajiNameWebService {
    @Inject
    private ReasonRomajiNameFinder finder;
    @Inject
    private ReasonRomajiNameCommandHandle handle;

    @POST
    @Path("/getReasonRomajiName/{empId}")
    public ReasonRomajiNameDto getReasonRomajiName(@PathParam("empId") String empId){
        return finder.getReasonRomajiName(empId);
    }

    @POST
    @Path("/updateReasonRomajiName")
    public void updateReasonRomajiName(ReasonRomajiNameCommand command){
        handle.handle(command);
    }

}
