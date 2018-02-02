package nts.uk.ctx.at.request.ws.application.applicationlatearrival;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationlatearrival.UpdateLateEarlyRequestCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestFinder;

/**
 * TanLV
 *
 */
@Path("at/request/application/applicationlatearrival")
@Produces("application/json")
public class LateEarlyRequestWebservice extends WebService {
	@Inject
	private LateEarlyRequestFinder finder;
	
	@Inject
	private UpdateLateEarlyRequestCommandHandler update;
	
	@POST
	@Path("findByCompanyID")
	public LateEarlyRequestDto findByID(){
		return this.finder.findByCompanyID();
	}
	
	@POST
	@Path("updateWithDrawalReqSet")
	public void updateLateEarlyRequest(LateEarlyRequestCommand command){
		this.update.handle(command);
	}
}
