package nts.uk.ctx.at.request.ws.applicationreason;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.gobackdirectly.InsertApplicationGoBackDirectlyCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommand;
import nts.uk.ctx.at.request.app.command.applicationreason.ApplicationReasonCommand;
import nts.uk.ctx.at.request.app.command.applicationreason.DeleteApplicationReasonCommand;
import nts.uk.ctx.at.request.app.command.applicationreason.DeleteApplicationReasonCommandHandler;
import nts.uk.ctx.at.request.app.command.applicationreason.InsertApplicationReasonCommandHandler;
import nts.uk.ctx.at.request.app.command.applicationreason.UpdateApplicationReasonCommand;
import nts.uk.ctx.at.request.app.command.applicationreason.UpdateApplicationReasonCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationsetting.ProxyAppSetCommand;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonFinder;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

@Path("at/request/application-reason")
@Produces("application/json")
public class ApplicationReasonService extends WebService{
	@Inject
	ApplicationReasonFinder reasonFinder;
	
	@Inject
	InsertApplicationReasonCommandHandler insert;
	
	@Inject
	UpdateApplicationReasonCommandHandler update;
	
	@Inject
	DeleteApplicationReasonCommandHandler delete;
	// find list application reason for KAF022
	@POST
	@Path("find/reason/{appType}")
	public List<ApplicationReasonDto> getReason(@PathParam("appType") int appType){
		return reasonFinder.getListApplicationReason(appType);
	}
	
	// update list application reason for KAF022
	@POST
	@Path("update")
	public void update(UpdateApplicationReasonCommand command){
		 this.update.handle(command);
	}
	
	// insert a item application reason for KAF022
	@POST
	@Path("insert")
	public JavaTypeResult<String> insert(ApplicationReasonCommand cmd){
		return new JavaTypeResult<String>(this.insert.handle(cmd));
	}
	
	// insert a item application reason for KAF022
	@POST
	@Path("delete")
	public void delete(DeleteApplicationReasonCommand deleteCmd) {
		this.delete.handle(deleteCmd);
	}
}
