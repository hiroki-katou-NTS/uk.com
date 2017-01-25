package nts.uk.ctx.pr.core.ws.retirement.payitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.RegisterRetirementPayItemCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.RegisterRetirementPayItemCommandHandler;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.RemoveRetirementPayItemCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.RemoveRetirementPayItemCommandHandler;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.UpdateRetirementPayItemCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payitem.UpdateRetirementPayItemCommandHandler;
import nts.uk.ctx.pr.core.app.find.retirement.payitem.RetirementPayItemFinder;
import nts.uk.ctx.pr.core.app.find.retirement.payitem.dto.RetirementPayItemDto;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("pr/core/retirement/payitem")
@Produces("application/json")
public class Qrm007WebService extends WebService{
	@Inject
	private RegisterRetirementPayItemCommandHandler registerRetirementPayItemCommandHandler;
	
	@Inject
	private RetirementPayItemFinder retirementPayItemFinder;
	
	@Inject
	private UpdateRetirementPayItemCommandHandler updateRetirementPayItemCommandHandler;
	
	@Inject
	private RemoveRetirementPayItemCommandHandler removeRetirementPayItemCommandHandler;
	
	@POST
	@Path("register")
	public void register(RegisterRetirementPayItemCommand command) {
		this.registerRetirementPayItemCommandHandler.handle(command);
	}
	
	@POST
	@Path("findAll")
	public List<RetirementPayItemDto> findAll() {
		return this.retirementPayItemFinder.findAll();
	}
	
	@POST
	@Path("findBycompanyCode")
	public List<RetirementPayItemDto> findBycompanyCode() {
		return this.retirementPayItemFinder.find_By_companyCode();
	}
	
	/*@POST
	@Path("update")
	public void update(List<UpdateRetirementPayItemCommand> command) {
		this.updateRetirementPayItemCommandHandler.handle(command);
	}*/
	
	@POST
	@Path("update")
	public void update(UpdateRetirementPayItemCommand command) {
		this.updateRetirementPayItemCommandHandler.handle(command);
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveRetirementPayItemCommand command) {
		this.removeRetirementPayItemCommandHandler.handle(command);
	}
}
