package nts.uk.ctx.basic.ws.system.bank;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.system.bank.AddBankCommand;
import nts.uk.ctx.basic.app.command.system.bank.AddBankCommandHandler;
import nts.uk.ctx.basic.app.command.system.bank.UpdateBankCommand;
import nts.uk.ctx.basic.app.command.system.bank.UpdateBankCommandHandler;
import nts.uk.ctx.basic.app.find.system.bank.BankFinder;
import nts.uk.ctx.basic.app.find.system.bank.dto.BankDto;

@Path("pr/core/system/bank")
@Produces("application/json")
public class BankWebService extends WebService {
	@Inject
	private AddBankCommandHandler addBankCommandHandler;

	@Inject
	private UpdateBankCommandHandler updateBankCommandHandler;
	@Inject
	private BankFinder bankFinder;
	
	@POST
	@Path("add")
	public void add(AddBankCommand command) {
		this.addBankCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateBankCommand command) {
		this.updateBankCommandHandler.handle(command);
	}
	
	@POST
	@Path("find/all")
	public List<BankDto> findAll() {
		return this.bankFinder.findAll();
	}
}
