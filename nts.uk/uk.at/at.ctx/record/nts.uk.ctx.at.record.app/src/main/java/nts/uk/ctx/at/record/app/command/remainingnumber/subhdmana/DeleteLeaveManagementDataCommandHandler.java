package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;

@Stateless
public class DeleteLeaveManagementDataCommandHandler extends CommandHandler<LeaveManagementDataCommand> {

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;

	@Override
	protected void handle(CommandHandlerContext<LeaveManagementDataCommand> context) {
		LeaveManagementDataCommand command = context.getCommand();
		
		// Delete 代出管理データ
		this.leaveManaDataRepository.deleteByLeaveId(command.getLeaveId());

		// Delete 休出代休紐付け管理
		this.leaveComDayOffManaRepository.deleteByLeaveId(command.getLeaveId());
	}
}
