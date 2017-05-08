package nts.uk.ctx.pr.report.ws.payment.comparing;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.DeleteComparingFormCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.DeleteComparingFormCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.InsertComparingFormCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.InsertComparingFormCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.UpdateComparingFormCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.command.UpdateComparingFormCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find.ComparingFormDto;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find.ComparingFormFinder;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find.ComparingFormHeaderDto;
import nts.uk.ctx.pr.report.app.payment.comparing.settingoutputitem.find.ComparingFormHeaderFinder;

@Path("report/payment/comparing")
@Produces("application/json")
public class ComparingFormHeaderWebService extends WebService {

	@Inject
	private ComparingFormHeaderFinder comparingFormHeaderFinder;
	@Inject
	private ComparingFormFinder comparingFormFinder;
	@Inject
	private InsertComparingFormCommandHandler insertComparing;
	@Inject
	private UpdateComparingFormCommandHandler updateComparing;
	@Inject
	private DeleteComparingFormCommandHandler deleteComparing;

	@POST
	@Path("find/formHeader")
	public List<ComparingFormHeaderDto> getListComparingFormHeader() {
		return this.comparingFormHeaderFinder.getListComparingFormHeader();
	}

	@POST
	@Path("getDataTab/{formCode}")
	public ComparingFormDto getDataForTab(@PathParam("formCode") String formCode) {
		return this.comparingFormFinder.findComparingForm(formCode);
	}

	@POST
	@Path("insertData")
	public void insertData(InsertComparingFormCommand insertComparingFormCommand) {
		this.insertComparing.handle(insertComparingFormCommand);
	}

	@POST
	@Path("updateData")
	public void updateData(UpdateComparingFormCommand updateComparingFormCommand) {
		this.updateComparing.handle(updateComparingFormCommand);
	}

	@POST
	@Path("deleteData")
	public void deleteData(DeleteComparingFormCommand deleteComparingFormCommand) {
		this.deleteComparing.handle(deleteComparingFormCommand);
	}
}
