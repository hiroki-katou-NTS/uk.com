package nts.uk.ctx.at.record.ws.knr.knr002.g;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.MakeTransferSelectedWorkTimesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.MakeTransferSelectedWorkTimesCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.MakeTransferSelectedWorkTypesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.g.MakeTransferSelectedWorkTypesCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr002.h.MakeSelectedEmployeesCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.h.MakeSelectedEmployeesCommandHandler;
import nts.uk.ctx.at.record.app.command.knr.knr002.k.MakeTransferSelectedBentoMenuFrameNumberCommand;
import nts.uk.ctx.at.record.app.command.knr.knr002.k.MakeTransferSelectedBentoMenuFrameNumberCommandHandler;

/**
 * 
 * @author xuannt
 *
 */
@Path("at/record/transferselecteddata")
@Produces("application/json")
public class MakeTranferSelectedDataWebService  extends WebService{
	
	//	選択した就業時間帯を送信データにする（Ｊデータ登録）CommandHandler.handle
	@Inject
	private MakeTransferSelectedWorkTimesCommandHandler makeTransferSelectedWorkTimesCommandHandler;
	
	//	選択した勤務種類を送信データにする（Ｉデータ登録）CommandHandler.handle
	@Inject
	MakeTransferSelectedWorkTypesCommandHandler makeTransferSelectedWorkTypesCommandHandler;
	
	//	選択した社員を送信データにするCommandHandler.handle
	@Inject
	MakeSelectedEmployeesCommandHandler makeSelectedEmployeesCommandHandler;
	
	//	選択した弁当メニュー枠番を送信データにする.CommandHandler.handle
	@Inject
	MakeTransferSelectedBentoMenuFrameNumberCommandHandler makeTransferSelectedBentoMenuFrameNumberCommandHandler;
	
	@POST
	@Path("worktimes")
	public void makeSelectedWorkTimes(MakeTransferSelectedWorkTimesCommand command) {
		this.makeTransferSelectedWorkTimesCommandHandler.handle(command);
	}
	
	@POST
	@Path("worktypes")
	public void makeSelectedWorkTypes(MakeTransferSelectedWorkTypesCommand command) {
		this.makeTransferSelectedWorkTypesCommandHandler.handle(command);
	}
	
	@POST
	@Path("employees")
	public void makeSelectedEmployees(MakeSelectedEmployeesCommand command) {
		this.makeSelectedEmployeesCommandHandler.handle(command);
	}
	
	@POST
	@Path("bentomenu")
	public void makeSelectedBentoMenu(MakeTransferSelectedBentoMenuFrameNumberCommand command) {
		this.makeTransferSelectedBentoMenuFrameNumberCommandHandler.handle(command);
	}
}
