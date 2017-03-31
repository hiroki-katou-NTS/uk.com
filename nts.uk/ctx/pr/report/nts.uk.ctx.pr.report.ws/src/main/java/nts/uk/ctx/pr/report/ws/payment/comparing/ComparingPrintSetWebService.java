package nts.uk.ctx.pr.report.ws.payment.comparing;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.DeleteComparingPrintSetCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.InsertComparingPrintSetCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.InsertComparingPrintSetCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.UpdateComparingPrintSetCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.UpdateComparingPrintSetCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.find.ComparingPrintSetFinder;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;

@Path("report/payment/comparing/setting")
@Produces("application/json")
public class ComparingPrintSetWebService extends WebService {
	@Inject
	private ComparingPrintSetFinder printSetFinder;
	@Inject
	private InsertComparingPrintSetCommandHandler insertPrintSet;
	@Inject
	private UpdateComparingPrintSetCommandHandler updatePrintSet;
	@Inject
	private DeleteComparingPrintSetCommandHandler deletePrintSet;

	@POST
	@Path("find")
	public Optional<ComparingPrintSet> getComparingPrintSet() {
		return this.printSetFinder.getComparingPrintSet();
	}

	@POST
	@Path("insertData")
	public void insertData(InsertComparingPrintSetCommand insertPrintSetCommand) {
		this.insertPrintSet.handle(insertPrintSetCommand);
	}

	@POST
	@Path("updateData")
	public void updateData(UpdateComparingPrintSetCommand updatePrintSetCommand) {
		this.updatePrintSet.handle(updatePrintSetCommand);
	}

	@POST
	@Path("deleteData")
	public void deleteData() {
		this.deletePrintSet.handle(null);
	}

}
