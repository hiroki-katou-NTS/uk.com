package nts.uk.ctx.at.request.ws.application.common.approvalframe;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameDto;
import nts.uk.ctx.at.request.app.find.application.common.approvalframe.ApprovalFrameFinder;
@Path("at/request/approvalframe")
@Produces("application/json")
public class ApprovalFrameService extends WebService {
	
	@Inject 
	private ApprovalFrameFinder frameFinder;

	@POST
	@Path("getallframe/{phaseID}")
	public List<ApprovalFrameDto> getAllFrameByPhaseID(@PathParam("phaseID") String phaseID){
		return this.frameFinder.getAllApproverByPhaseID(phaseID);
	}
	
	@POST
	@Path("getallframebylistphaseid")
	public List<ApprovalFrameDto> getAllFrameByListPhaseID(List<String> listPhaseID){
		return this.frameFinder.getAllApproverByListPhaseID(listPhaseID);
	}
	

}
