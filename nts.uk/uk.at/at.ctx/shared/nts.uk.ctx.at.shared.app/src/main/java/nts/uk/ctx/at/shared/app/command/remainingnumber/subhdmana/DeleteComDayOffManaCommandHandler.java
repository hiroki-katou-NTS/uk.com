package nts.uk.ctx.at.shared.app.command.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;

/**
 * @author sang.nv
 *
 */
@Stateless
public class DeleteComDayOffManaCommandHandler extends CommandHandler<CompensatoryDayOffManaDataCommand> {

	@Inject
	private ComDayOffManaDataRepository comDayRepo;

	@Override
	protected void handle(CommandHandlerContext<CompensatoryDayOffManaDataCommand> context) {
		CompensatoryDayOffManaDataCommand command = context.getCommand();

		//Delete domain 代休管理データ
		this.comDayRepo.deleteByComDayOffId(command.getComDayOffID());
	}
}
