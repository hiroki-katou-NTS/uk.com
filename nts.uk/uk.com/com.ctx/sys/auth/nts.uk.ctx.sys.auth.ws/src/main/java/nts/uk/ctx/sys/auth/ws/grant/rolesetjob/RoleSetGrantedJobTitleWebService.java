package nts.uk.ctx.sys.auth.ws.grant.rolesetjob;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.app.command.grant.RegisterRoleSetGrantedJobTitleCommandHandler;
import nts.uk.ctx.sys.auth.app.command.grant.RoleSetGrantedJobTitleCommand;
import nts.uk.ctx.sys.auth.app.find.grant.GrantRoleSetJobDto;
import nts.uk.ctx.sys.auth.app.find.grant.RoleSetGrantedJobTitleFinder;

/**
 * 
 * @author HungTT
 *
 */

@Path("ctx/sys/auth/grant/rolesetjob")
@Produces("application/json")
public class RoleSetGrantedJobTitleWebService extends WebService {

	@Inject
	private RoleSetGrantedJobTitleFinder finder;
	
	@Inject
	private RegisterRoleSetGrantedJobTitleCommandHandler handler;
	
	@POST
	@Path("start")
	public GrantRoleSetJobDto start(GeneralDate refDate){
		return this.finder.getAllData(refDate);
	}
	
	@POST
	@Path("register")
	public void register(RoleSetGrantedJobTitleCommand command){
		this.handler.handle(command);		
	}
	
}
