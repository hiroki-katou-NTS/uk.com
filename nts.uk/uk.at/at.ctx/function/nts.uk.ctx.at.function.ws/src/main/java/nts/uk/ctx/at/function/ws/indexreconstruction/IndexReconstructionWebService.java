package nts.uk.ctx.at.function.ws.indexreconstruction;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.indexreconstruction.ProExecIndexDtoAndNumberTargetTableDto;
import nts.uk.ctx.at.function.app.find.indexreconstruction.ProcExecIndexFinder;

@Path("at/function/indexreconstruction")
@Produces("application/json")
public class IndexReconstructionWebService extends WebService {
	
    @Inject
    private ProcExecIndexFinder procExecIndexFinder;

    @Path("/{execId}")
    @POST
    public ProExecIndexDtoAndNumberTargetTableDto getIndexReconstructionAndNumberTarget(@PathParam("execId") String execId) {
    	return this.procExecIndexFinder.findByExectionId(execId);
    }
}
