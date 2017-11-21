package nts.uk.ctx.sys.auth.ws.grant.rolesetjob;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.app.command.grant.rolesetjob.RegisterRoleSetGrantedJobTitleCommandHandler;
import nts.uk.ctx.sys.auth.app.command.grant.rolesetjob.RoleSetGrantedJobTitleCommand;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.GrantRoleSetJobDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.JobTitleDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.ReferenceDateDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.RoleSetDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.RoleSetGrantedJobTitleDto;
import nts.uk.ctx.sys.auth.app.find.grant.rolesetjob.RoleSetGrantedJobTitleFinder;

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
	public GrantRoleSetJobDto start(ReferenceDateDto refDate){
		//return this.finder.getAllData(refDate);
		System.out.println("refDate: " + refDate.getRefDate().toString());
		List<RoleSetDto> listRS = new ArrayList<>();
		for (int i = 1; i < 10; i++){listRS.add(new RoleSetDto(i < 10 ? "0" + i : "10", i < 10 ? "role set 0" + i : "role set 10"));}
		List<JobTitleDto> listJT = new ArrayList<>();
		for (int i = 1; i < 10; i++){listJT.add(new JobTitleDto("00000000" + i, i < 10 ? "0" + i : "10", i < 10 ? "job title 0" + i : "role set 10"));}
		
		return new GrantRoleSetJobDto(listRS, new RoleSetGrantedJobTitleDto(true, null), listJT);
	}
	
	@POST
	@Path("register")
	public void register(RoleSetGrantedJobTitleCommand command){
		//this.handler.handle(command);
		
		System.out.println("register done! - command: " + command.isApplyToConcurrentPerson() + " - details lengt: " + command.getDetails().size());
	}
	
}
