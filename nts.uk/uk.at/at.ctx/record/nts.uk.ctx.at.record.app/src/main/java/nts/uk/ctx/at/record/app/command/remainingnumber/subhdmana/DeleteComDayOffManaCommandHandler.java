package nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;

/**
 * @author sang.nv
 *
 */
@Stateless
public class DeleteComDayOffManaCommandHandler extends CommandHandler<CompensatoryDayOffManaDataCommand> {

	@Inject
	private ComDayOffManaDataRepository comDayRepo;

	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;

	@Override
	protected void handle(CommandHandlerContext<CompensatoryDayOffManaDataCommand> context) {
		CompensatoryDayOffManaDataCommand command = context.getCommand();

		//Delete domain 代休管理データ
		this.comDayRepo.deleteByComDayOffId(command.getComDayOffID());

		//Delete domain 休出代休紐付け管理
		this.leaveComDayOffManaRepository.deleteByComDayOffId(command.getComDayOffID());
	}
}
