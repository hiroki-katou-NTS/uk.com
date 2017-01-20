package nts.uk.ctx.pr.core.ws.rule.employment.avepay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.RegisterAvePayCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.RegisterAvePayCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.RemoveAvePayCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.RemoveAvePayCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.UpdateAvePayCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.UpdateAvePayCommandHandler;
import nts.uk.ctx.pr.core.app.find.rule.employment.avepay.AvePayFinder;
import nts.uk.ctx.pr.core.app.find.rule.employment.avepay.dto.AvePayDto;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("pr/core/avepay")
@Produces("application/json")
public class Qmm018WebService extends WebService {
	
	@Inject
	private RegisterAvePayCommandHandler registerAvePayCommandHandler;
	
	@Inject
	private UpdateAvePayCommandHandler updateAvePayCommandHandler;
	
	@Inject
	private AvePayFinder avePayFinder;
	
	@Inject
	private RemoveAvePayCommandHandler removeAvePayCommandHandler;
	
	@POST
	@Path("register")
	public void register(RegisterAvePayCommand command) {
		this.registerAvePayCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateAvePayCommand command) {
		this.updateAvePayCommandHandler.handle(command);
	}
	
	/*@POST
	@Path("find")
	public AvePayDto find() {
		return this.avePayFinder.find();
	}*/
	
	@POST
	@Path("findAll")
	public List<AvePayDto> findAll() {
		return this.avePayFinder.findAll();
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveAvePayCommand command) {
		this.removeAvePayCommandHandler.handle(command);
	}
}
