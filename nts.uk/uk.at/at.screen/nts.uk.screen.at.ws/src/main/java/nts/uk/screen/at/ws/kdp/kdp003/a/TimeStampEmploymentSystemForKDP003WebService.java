package nts.uk.screen.at.ws.kdp.kdp003.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.kdp.kdp001.a.ConfirmUseOfStampInputResult;
import nts.uk.ctx.at.record.app.command.kdp.kdp003.a.ConfirmUseOfStampInputCommandWithEmployeeId;
import nts.uk.ctx.at.record.app.command.kdp.kdp003.a.ConfirmUseOfStampInputWithEmployeeIdCommandHandler;
import nts.uk.ctx.at.record.app.command.kdp.kdp003.a.RegisterNameSelectionCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp003.a.RegisterNameSelectionCommandHandler;

@Path("at/record/stamp/employment/system")
@Produces("application/json")
public class TimeStampEmploymentSystemForKDP003WebService extends WebService {

	@Inject
	private RegisterNameSelectionCommandHandler registerHandler;

	@Inject
	private ConfirmUseOfStampInputWithEmployeeIdCommandHandler confirmHandler;

	/**
	 * 打刻入力(ポータル)を登録する
	 */
	@POST
	@Path("register-stamp-input")
	public GeneralDate registerStampInput(RegisterNameSelectionCommand command) {
		return this.registerHandler.handle(command);
	}

	/**
	 * 打刻入力を利用できるかを確認する
	 * 
	 * @param command
	 * @return
	 */
	@POST
	@Path("confirm-use-of-stamp-input")
	public ConfirmUseOfStampInputResult confirmUseOfStampInput(ConfirmUseOfStampInputCommandWithEmployeeId command) {
		return this.confirmHandler.handle(command);
	}
}
