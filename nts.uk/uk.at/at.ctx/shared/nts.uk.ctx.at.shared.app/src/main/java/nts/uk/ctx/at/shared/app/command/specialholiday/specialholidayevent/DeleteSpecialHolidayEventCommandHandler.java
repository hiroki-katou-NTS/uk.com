package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteSpecialHolidayEventCommandHandler extends CommandHandler<DeleteSpecialHolidayEventCommand> {

	@Inject
	private SpecialHolidayEventRepository sHEventRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteSpecialHolidayEventCommand> context) {
		// アルゴリズム「削除時処理」を実行する(thực hiện xử lý 「削除時処理」)

		DeleteSpecialHolidayEventCommand cmd = context.getCommand();

		String companyId = AppContexts.user().companyId();
		this.sHEventRepo.remove(companyId, cmd.getSpecialHolidayEventNo());
	}

}
