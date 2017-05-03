package nts.uk.ctx.at.record.ws.divergencetime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceReasonDto;
import nts.uk.ctx.at.record.app.find.divergencetime.DivergenceReasonFinder;

@Path("at/record/divergencetime")
@Produces("application/json")
public class DivergenceTimeWebService extends WebService{

	@Inject
	private DivergenceReasonFinder getAllDivReason;
	
	/**
	 * get all divergence reason
	 * @param divTimeId
	 * @return
	 */
	@POST
	@Path("getalldivreason/{divTimeId}")
	public List<DivergenceReasonDto> getAllDivReason(@PathParam("divTimeId") String divTimeId){
		return this.getAllDivReason.getAllDivReasonByCode(divTimeId);
	}
}
