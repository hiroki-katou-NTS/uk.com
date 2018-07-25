package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;

@Stateless
public class DeleteLeaveManagementDataCommandHandler extends CommandHandler<LeaveManagementDataCommand> {

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	@Override
	protected void handle(CommandHandlerContext<LeaveManagementDataCommand> context) {
		LeaveManagementDataCommand command = context.getCommand();
		
		// Delete 代出管理データ
		this.leaveManaDataRepository.deleteByLeaveId(command.getLeaveId());
	}
}
