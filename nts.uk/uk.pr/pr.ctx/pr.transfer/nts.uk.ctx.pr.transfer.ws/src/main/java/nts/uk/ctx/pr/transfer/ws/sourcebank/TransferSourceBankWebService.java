package nts.uk.ctx.pr.transfer.ws.sourcebank;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.transfer.app.command.sourcebank.RegisterSourceBankCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.sourcebank.RemoveSourceBankCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.sourcebank.TransferSourceBankCommand;
import nts.uk.ctx.pr.transfer.app.find.sourcebank.TransferSourceBankDto;
import nts.uk.ctx.pr.transfer.app.find.sourcebank.TransferSourceBankFinder;

@Path("ctx/pr/transfer/sourcebank")
@Produces("application/json")
public class TransferSourceBankWebService extends WebService {

	@Inject
	private TransferSourceBankFinder finder;

	@Inject
	private RegisterSourceBankCommandHandler regHandler;

	@Inject
	private RemoveSourceBankCommandHandler removeHandler;

	@POST
	@Path("get-all-source-bank")
	public List<TransferSourceBankDto> getAllResidentTaxPayee() {
		return finder.getAll();
	}

	@POST
	@Path("get-source-bank/{code}")
	public TransferSourceBankDto getSourceBank(@PathParam("code") String code) {
		return finder.getTransferSourceBank(code);
	}

	@POST
	@Path("reg-source-bank")
	public void getSourceBank(TransferSourceBankCommand sourceBank) {
		regHandler.handle(sourceBank);
	}

	@POST
	@Path("check-before-delete/{code}")
	public void checkBeforeDelete(@PathParam("code") String code) {
		finder.checkBeforeDelete(code);
	}
	
	@POST
	@Path("del-source-bank/{code}")
	public void removeSourceBank(@PathParam("code") String code) {
		removeHandler.handle(code);
	}

}
