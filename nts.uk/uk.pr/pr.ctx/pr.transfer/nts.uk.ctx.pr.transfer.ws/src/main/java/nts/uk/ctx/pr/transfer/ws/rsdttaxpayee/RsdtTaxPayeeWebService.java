package nts.uk.ctx.pr.transfer.ws.rsdttaxpayee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.DeleteListResidentTaxPayeeCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.DeleteResidentTaxPayeeCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.RtpIntegrationCommand;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.RegisterResidentTaxPayeeCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.ResidentTaxPayeeCommand;
import nts.uk.ctx.pr.transfer.app.command.rsdttaxpayee.ResidentTaxPayeeIntegrationCommandHandler;
import nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee.ResidentTaxPayeeDto;
import nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee.ResidentTaxPayeeFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/pr/transfer/rsdttaxpayee")
@Produces("application/json")
public class RsdtTaxPayeeWebService extends WebService {

	@Inject
	private ResidentTaxPayeeFinder finder;

	@Inject
	private RegisterResidentTaxPayeeCommandHandler regHandler;

	@Inject
	private DeleteResidentTaxPayeeCommandHandler delHandler;
	
	@Inject
	private DeleteListResidentTaxPayeeCommandHandler delListHandler;
	
	@Inject
	private ResidentTaxPayeeIntegrationCommandHandler rsdTaxPayeeIntegrationHanler;

	@POST
	@Path("get-all-resident-tax-payee")
	public List<ResidentTaxPayeeDto> getAllResidentTaxPayee() {
		return finder.getAll();
	}
	
	@POST
	@Path("get-all-resident-tax-payee-company-zero")
	public List<ResidentTaxPayeeDto> getAllResidentTaxPayeeCompanyZero() {
		return finder.getAllCompanyZero();
	}

	@POST
	@Path("get-resident-tax-payee/{code}")
	public ResidentTaxPayeeDto getRsdtTaxPayee(@PathParam("code") String code) {
		return finder.getResidentTaxPayee(code);
	}
	
	@POST
	@Path("get-resident-tax-payee-company-zero/{code}")
	public ResidentTaxPayeeDto getRsdtTaxPayeeCompanyZero(@PathParam("code") String code) {
		return finder.getResidentTaxPayeeCompanyZero(code);
	}

	@POST
	@Path("reg-resident-tax-payee")
	public void registerBank(ResidentTaxPayeeCommand residentTaxPayee) {
		regHandler.handle(residentTaxPayee);
	}

	@POST
	@Path("check-before-delete")
	public void checkBeforeDelete(List<String> codes) {
		finder.checkBeforeDelete(AppContexts.user().companyId(), codes);
	}
	
	@POST
	@Path("delete-resident-tax-payee/{code}")
	public void deleteBranch(@PathParam("code") String code) {
		delHandler.handle(code);
	}
	
	@POST
	@Path("delete-list")
	public void deleteList(List<String> codes) {
		delListHandler.handle(codes);
	}
	
	@POST
	@Path("integration")
	public void integration(RtpIntegrationCommand command) {
		rsdTaxPayeeIntegrationHanler.handle(command);
	}

}
