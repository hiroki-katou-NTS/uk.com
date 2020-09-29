package nts.uk.ctx.at.shared.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.remainingnumber.DeletePaymentManagementDataCmdHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.DeletePaymentManagementDataCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.DisplayRemainingNumberDataInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.FurikyuMngDataExtractionService;

@Path("at/record/remainnumber/paymana")
@Produces("application/json")
public class FurikyuMngDataExtractionWebService extends WebService {
	
	@Inject
	private FurikyuMngDataExtractionService finder;
	
	@Inject
	private DeletePaymentManagementDataCmdHandler deletePaymentManagementDataHandler;
	
	@POST
	@Path("getFurikyuMngDataExtraction/{empId}/{isPeriod}")
	public DisplayRemainingNumberDataInformation getFurikyuMngDataExtraction(@PathParam("empId") String empId,
			@PathParam("isPeriod") boolean isPeriod) {
		return finder.getFurikyuMngDataExtractionUpdate(empId, isPeriod);
	}
	
	@POST
	@Path("deletePaymentManagementData")
	public void deletePaymentManagementData(DeletePaymentManagementDataCommand command) {
		this.deletePaymentManagementDataHandler.handle(command);
	}	
	
}
