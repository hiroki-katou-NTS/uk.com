package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@Stateless
public class DeleteLeaveManagementDataCommandHandler extends CommandHandler<LeaveManagementDataCommand> {

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;

	@Override
	protected void handle(CommandHandlerContext<LeaveManagementDataCommand> context) {
		LeaveManagementDataCommand command = context.getCommand();
	
		// ドメインモデル「休出管理データ」を取得
		Optional<LeaveManagementData> leaveMana = leaveManaDataRepository.getByLeaveId(command.getLeaveId());
		
		// ドメインモデル「休出管理データ」を削除
		if (leaveMana.isPresent()) {
			leaveManaDataRepository.deleteByLeaveId(command.getLeaveId());
		}
		
		// ドメインモデル「代休管理データ」を取得
		Optional<CompensatoryDayOffManaData> comDayOff = comDayOffManaDataRepository.getBycomdayOffId(command.getComDayOffID());
		
		// ドメインモデル「代休管理データ」を削除
		if (comDayOff.isPresent()) {
			comDayOffManaDataRepository.deleteByComDayOffId(command.getComDayOffID());
		}
		
		// ドメインモデル「休出代休紐付け管理」を削除
		if (leaveMana.isPresent() && comDayOff.isPresent()) {
			leaveComDayOffManaRepository.delete(leaveMana.get().getID(),
					leaveMana.get().getComDayOffDate().getDayoffDate().orElse(null),
					comDayOff.get().getDayOffDate().getDayoffDate().orElse(null));
			
			leaveComDayOffManaRepository.delete(comDayOff.get().getComDayOffID(),
					leaveMana.get().getComDayOffDate().getDayoffDate().get(),
					comDayOff.get().getDayOffDate().getDayoffDate().get());
		}
		
	}
}
