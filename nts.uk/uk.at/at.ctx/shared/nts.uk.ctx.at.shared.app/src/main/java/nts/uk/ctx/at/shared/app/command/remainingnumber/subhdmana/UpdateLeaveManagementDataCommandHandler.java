package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;

@Stateless
public class UpdateLeaveManagementDataCommandHandler extends CommandHandler<LeaveManagementDataCommand> {

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	@Override
	protected void handle(CommandHandlerContext<LeaveManagementDataCommand> context) {
		LeaveManagementDataCommand command = context.getCommand();
		// ドメインモデル「休出管理データ」の選択データを更新する
		this.leaveManaDataRepository.udpateByHolidaySetting(command.getLeaveId(), command.getIsCheckedExpired(),
				command.getExpiredDate(), command.getOccurredDays(), command.getUnUsedDays());
	}
}
