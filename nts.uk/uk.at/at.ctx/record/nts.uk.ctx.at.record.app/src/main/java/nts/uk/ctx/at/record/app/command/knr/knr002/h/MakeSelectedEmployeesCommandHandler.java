package nts.uk.ctx.at.record.app.command.knr.knr002.h;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.NRリモート.App.Ｈ：選択した社員を送信データにする
 * @author xuannt
 *
 */
@Stateless
public class MakeSelectedEmployeesCommandHandler extends CommandHandler<MakeSelectedEmployeesCommand>{

	@Inject
	DeleteEmpsOfLoginCompanyCommandHandler deleteEmpsOfLoginCompanyCommandHandler;
	@Inject
	UpdateSelectedEmployeeCommandHandler updateSelectedEmployeeCommandHandler;
	
	@Override
	protected void handle(CommandHandlerContext<MakeSelectedEmployeesCommand> context) {
		MakeSelectedEmployeesCommand command = context.getCommand();
		EmpInfoTerminalCode terminalCode =command.getTerminalCode();
		List<EmployeeId> selectedEmployeesID = command.getSelectedEmployeesID();
		List<EmployeeId> loginCompanyEmpIds = command.getLoginCompanyEmployeesID();
		//	1. ログイン社員の社員IDを削除(契約コード、就業情報端末コード、ログイン会社の社員ID(List))
		this.deleteEmpsOfLoginCompanyCommandHandler.handle(new DeleteEmpsOfLoginCompanyCommand(terminalCode, loginCompanyEmpIds));
		//	2. 送信データを選択した社員IDにUpdate(契約コード、就業情報端末コード、選択した社員ID(List))
		this.updateSelectedEmployeeCommandHandler
			.handle(new UpdateSelectedEmployeeCommand(terminalCode, selectedEmployeesID));
	}
}
