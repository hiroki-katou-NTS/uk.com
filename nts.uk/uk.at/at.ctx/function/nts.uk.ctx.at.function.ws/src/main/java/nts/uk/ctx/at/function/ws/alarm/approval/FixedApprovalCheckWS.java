package nts.uk.ctx.at.function.ws.alarm.approval;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.alarm.approval.FixedApprovalFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AppApprovalFixedExtractItemDto;

@Path("at/function/alarm/approval")
@Produces("application/json")
public class FixedApprovalCheckWS extends WebService {

	@Inject
	private FixedApprovalFinder finder;
	
	@POST
	@Path("findallfixedapprovalcheckitem")
	public List<AppApprovalFixedExtractItemDto> findAllFixedApprovalItem(){
		List<AppApprovalFixedExtractItemDto> data = finder.findAll();
		return data;
	}
}
