package nts.uk.ctx.pr.core.ws.rule.employment.avepay;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.AddAvePayCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.AddAvePayCommandHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.UpdateAvePayCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.avepay.UpdateAvePayCommandHandler;
import nts.uk.ctx.pr.core.app.find.rule.employment.avepay.AvePayFinder;
import nts.uk.ctx.pr.core.app.find.rule.employment.avepay.dto.AvePayDto;

@Path("pr/core/avepay")
@Produces("application/json")
public class Qmm018WebService extends WebService {
	
	@Inject
	private AddAvePayCommandHandler addAvePayCommandHandler;
	@Inject
	private UpdateAvePayCommandHandler updateAvePayCommandHandler;
	
	@Inject
	private AvePayFinder avePayFinder;
	
	@Path("add")
	public void add(AddAvePayCommand command) {
		this.addAvePayCommandHandler.handle(command);
	}
	
	@Path("update")
	public void add(UpdateAvePayCommand command) {
//		this.updateAvePayCommandHandler.handle(command);
	}
	
	@Path("find")
	public AvePayDto find() {
		return this.avePayFinder.find();
	}
}
