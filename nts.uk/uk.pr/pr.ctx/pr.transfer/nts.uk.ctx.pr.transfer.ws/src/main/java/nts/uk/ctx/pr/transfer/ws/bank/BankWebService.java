package nts.uk.ctx.pr.transfer.ws.bank;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.transfer.app.find.bank.BankBranchDto;
import nts.uk.ctx.pr.transfer.app.find.bank.BankDto;
import nts.uk.ctx.pr.transfer.app.command.bank.BankBranchCommand;
import nts.uk.ctx.pr.transfer.app.command.bank.BankCommand;
import nts.uk.ctx.pr.transfer.app.command.bank.DeleteBankBranchCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.bank.DeleteListBankBranchCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.bank.RegisterBankBranchCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.bank.RegisterBankCommandHandler;
import nts.uk.ctx.pr.transfer.app.find.bank.BankAndBranchFinder;

/**
 * 
 * @author HungTT
 *
 */

@Path("ctx/pr/transfer/bank")
@Produces("application/json")
public class BankWebService extends WebService {

	@Inject
	private BankAndBranchFinder bankFinder;

	@Inject
	private RegisterBankCommandHandler regBankHandler;

	@Inject
	private RegisterBankBranchCommandHandler regBranchHandler;
	
	@Inject
	private DeleteBankBranchCommandHandler deleteHandler;
	
	@Inject
	private DeleteListBankBranchCommandHandler deleteListHandler;

	@POST
	@Path("get-all-bank")
	public List<BankDto> getAllBank() {
		return bankFinder.getAllBank();
	}

	@POST
	@Path("get-all-bank-branch")
	public List<BankBranchDto> getAllBankBranch(List<String> bankCodes) {
		return bankFinder.getAllBankBranch(bankCodes);
	}

	@POST
	@Path("get-bank-branch/{branchId}")
	public BankBranchDto completeConfirm(@PathParam("branchId") String id) {
		return bankFinder.getBankBranchById(id);
	}

	@POST
	@Path("reg-bank")
	public void registerBank(BankCommand bank) {
		regBankHandler.handle(bank);
	}

	@POST
	@Path("reg-bank-branch")
	public JavaTypeResult<String> registerBankBranch(BankBranchCommand bankBranch) {
		return new JavaTypeResult<String>(regBranchHandler.handle(bankBranch));
	}

	@POST
	@Path("check-before-delete-branch/{branchId}")
	public void checkBeforeDeleteBranch(@PathParam("branchId") String branchId) {
		bankFinder.checkBeforDeleteBranch(branchId);
	}
	
	@POST
	@Path("delete-branch/{branchId}")
	public void deleteBranch(@PathParam("branchId") String branchId) {
		deleteHandler.handle(branchId);
	}
	
	@POST
	@Path("delete-list-branch")
	public void deleteBranch(List<String> targets) {
		deleteListHandler.handle(targets);
	}

}
