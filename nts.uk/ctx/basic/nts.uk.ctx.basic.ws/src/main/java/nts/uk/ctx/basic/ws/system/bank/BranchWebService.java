package nts.uk.ctx.basic.ws.system.bank;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.system.bank.branch.AddBranchCommand;
import nts.uk.ctx.basic.app.command.system.bank.branch.AddBranchCommandHandler;
import nts.uk.ctx.basic.app.command.system.bank.branch.UpdateBranchCommand;
import nts.uk.ctx.basic.app.command.system.bank.branch.UpdateBranchCommandHandler;

@Path("basic/system/bank/branch")
@Produces("application/json")
public class BranchWebService extends WebService {
	@Inject
	private AddBranchCommandHandler addBranchCommandHandler;
	
	@Inject
	private UpdateBranchCommandHandler updateBranchCommandHandler;
    
	@POST
	@Path("add")
	public void add(AddBranchCommand command){
	   this.addBranchCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateBranchCommand command){
	   this.updateBranchCommandHandler.handle(command);
	}
}
