package nts.uk.ctx.at.request.ws.applicationreason;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

@Path("at/request/application-reason")
@Produces("application/json")
public class ApplicationReasonService extends WebService{
//	@Inject
//	ApplicationReasonFinder reasonFinder;
//	
//	@Inject
//	InsertApplicationReasonCommandHandler insert;
//	
//	@Inject
//	UpdateApplicationReasonCommandHandler update;
	
//	@Inject
//	DeleteApplicationReasonCommandHandler delete;
	// find list application reason for KAF022
//	@POST
//	@Path("find/reason/{appType}")
//	public List<ApplicationReasonDto> getReason(@PathParam("appType") int appType){
//		return reasonFinder.getListApplicationReason(appType);
//	}
	
	// update list application reason for KAF022
//	@POST
//	@Path("update")
//	public void update(UpdateApplicationReasonCommand command){
//		 this.update.handle(command);
//	}
//	
//	// insert a item application reason for KAF022
//	@POST
//	@Path("insert")
//	public JavaTypeResult<String> insert(ApplicationReasonCommand cmd){
//		return new JavaTypeResult<String>(this.insert.handle(cmd));
//	}
	
	// insert a item application reason for KAF022
//	@POST
//	@Path("delete")
//	public void delete(DeleteApplicationReasonCommand deleteCmd) {
//		this.delete.handle(deleteCmd);
//	}
}
