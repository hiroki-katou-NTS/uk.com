package nts.uk.ctx.pr.core.ws.retirement.payment;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.retirement.payment.RegisterRetirementPaymentCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payment.RegisterRetirementPaymentCommandHandler;
import nts.uk.ctx.pr.core.app.command.retirement.payment.RemoveRetirementPaymentCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payment.RemoveRetirementPaymentCommandHandler;
import nts.uk.ctx.pr.core.app.command.retirement.payment.UpdateRetirementPaymentCommand;
import nts.uk.ctx.pr.core.app.command.retirement.payment.UpdateRetirementPaymentCommandHandler;
import nts.uk.ctx.pr.core.app.find.retirement.payment.RetirementPaymentFinder;
import nts.uk.ctx.pr.core.app.find.retirement.payment.dto.RetirementPaymentDto;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Path("pr/core/retirement/payment")
@Produces("application/json")
public class Qrm001WebService extends WebService{
	@Inject
	private RegisterRetirementPaymentCommandHandler registerRetirementPaymentCommandHandler;
	
	@Inject
	private RetirementPaymentFinder retirementPaymentFinder;
	
	@Inject
	private UpdateRetirementPaymentCommandHandler updateRetirementPaymentCommandHandler;
	
	@Inject
	private RemoveRetirementPaymentCommandHandler removeRetirementPaymentCommandHandler;
	
	@Path("register")
	public void register(RegisterRetirementPaymentCommand command){
		this.registerRetirementPaymentCommandHandler.handle(command);
	}
	
	@Path("findAll")
	public List<RetirementPaymentDto> findAll(){
		return this.retirementPaymentFinder.findAll();
	}
	
	@Path("update")
	public void update(UpdateRetirementPaymentCommand command){
		this.updateRetirementPaymentCommandHandler.handle(command);
	}
	
	@Path("remove")
	public void remove(RemoveRetirementPaymentCommand command){
		this.removeRetirementPaymentCommandHandler.handle(command);
	}
}
