package nts.uk.ctx.at.request.ws.application.withdrawalrequestset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset.UpdateWithDrawalReqSetCommandHandler;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetFinder;

@Path("at/request/application/withdrawalrequestset")
@Produces("application/json")
public class WithDrawalReqSetWebservice extends WebService {
	@Inject
	private WithDrawalReqSetFinder finder;
	
	@Inject
	private UpdateWithDrawalReqSetCommandHandler update;
	
	@POST
	@Path("findByCompanyID")
	public WithDrawalReqSetDto findByID(){
		return this.finder.findByCompanyID();
	}
	
	@POST
	@Path("updateWithDrawalReqSet")
	public void updateWithDrawalReqSet(UpdateWithDrawalReqSetCommand command){
		this.update.handle(command);
	}
}
