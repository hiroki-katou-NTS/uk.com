package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import java.util.List;
import java.util.stream.Collectors;

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
public class DeleteLeaveManagementDataCommandHandler extends CommandHandler<DeleteLeaveManagementDataCommand> {

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteLeaveManagementDataCommand> context) {
		DeleteLeaveManagementDataCommand command = context.getCommand();

		// ドメインモデル「休出管理データ」を取得
		List<LeaveManagementData> leaveMana = this.leaveManaDataRepository.getListByLeaveId(command.getLeaveId());

		// ドメインモデル「休出管理データ」を削除
		if (!leaveMana.isEmpty()) {
			this.leaveManaDataRepository.deleteById(command.getLeaveId());
		}

		// ドメインモデル「代休管理データ」を取得
		List<CompensatoryDayOffManaData> comDayOff = this.comDayOffManaDataRepository.getListComdayOffId(command.getComDayOffID());

		// ドメインモデル「代休管理データ」を削除
		if (!comDayOff.isEmpty()) {
			this.comDayOffManaDataRepository.deleteById(command.getComDayOffID());
		}

		// ドメインモデル「休出代休紐付け管理」を削除
		if (!leaveMana.isEmpty() && !comDayOff.isEmpty()) {
			this.leaveComDayOffManaRepository.delete(leaveMana.size() > 0 ? leaveMana.get(0).getSID() : null,
					comDayOff.size() > 0 ? comDayOff.get(0).getSID() : null,
					leaveMana.stream().map(x -> x.getComDayOffDate().getDayoffDate().orElse(null)).collect(Collectors.toList()),
					comDayOff.stream().map(x-> x.getDayOffDate().getDayoffDate().orElse(null)).collect(Collectors.toList()));
		}
	}

}
