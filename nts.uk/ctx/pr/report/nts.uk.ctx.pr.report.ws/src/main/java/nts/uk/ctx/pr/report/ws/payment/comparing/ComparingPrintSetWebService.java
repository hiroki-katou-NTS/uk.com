package nts.uk.ctx.pr.report.ws.payment.comparing;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.DeletePrintSettingCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.InsertUpdatePrintSettingCommand;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.command.InsertUpdatePrintSettingCommandHandler;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.find.ComparingPrintSetDto;
import nts.uk.ctx.pr.report.app.payment.comparing.setting.find.ComparingPrintSetFinder;

@Path("report/payment/comparing/setting")
@Produces("application/json")
public class ComparingPrintSetWebService extends WebService {

	@Inject
	private ComparingPrintSetFinder printSettingFinder;
	@Inject
	private InsertUpdatePrintSettingCommandHandler insertUpdatePrintSetting;
	@Inject
	private DeletePrintSettingCommandHandler deletePrintSetting;

	@POST
	@Path("find")
	public ComparingPrintSetDto getComparingPrintSet() {
		return this.printSettingFinder.getComparingPrintSet();
	}

	@POST
	@Path("insertUpdateData")
	public void insertUpdateData(InsertUpdatePrintSettingCommand insertUpdatePrintSet) {
		this.insertUpdatePrintSetting.handle(insertUpdatePrintSet);
	}

	@POST
	@Path("deleteData")
	public void deleteData() {
		this.deletePrintSetting.handle(null);
	}

}
