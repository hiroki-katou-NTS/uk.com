package nts.uk.ctx.pr.core.ws.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ReflectSystemReferenceDateInfoDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.ReflectSystemReferenceDateInfoFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("ctx/pr/processdatecls")
@Produces(MediaType.APPLICATION_JSON)
public class ReflectSystemReferenceDateInfoWebService {
    @Inject
    private ReflectSystemReferenceDateInfoFinder finder;

    @POST
    @Path("findReflectSystemReferenceDateInfo/{processingCategoryNo}/{processDate}")
    public ReflectSystemReferenceDateInfoDto findDisplayRegister(@PathParam("processingCategoryNo") int processCateNo,
                                                                 @PathParam("processDate") int processDate) {
        return finder.getReflectSystemReferenceDateInfoDto(processCateNo, processDate);
    }
}
