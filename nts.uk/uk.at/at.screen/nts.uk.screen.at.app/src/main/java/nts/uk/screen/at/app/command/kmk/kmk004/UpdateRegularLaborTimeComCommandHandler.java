package nts.uk.screen.at.app.command.kmk.kmk004;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).法定労働時間.法定労働時間（New）.週日法定労働時間.通常勤務.APP.会社別通常勤務法定労働時間を更新する.会社別通常勤務法定労働時間を更新する
 */
@Stateless
public class UpdateRegularLaborTimeComCommandHandler extends CommandHandler<UpdateRegularLaborTimeComCommand> {

	@Inject
	private RegularLaborTimeComRepo regularLaborTimeComRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateRegularLaborTimeComCommand> context) {

		UpdateRegularLaborTimeComCommand cmd = context.getCommand();

		// 1. Update
		RegularLaborTimeCom domain = cmd.toDomain();

		this.regularLaborTimeComRepo.update(domain);
	}

}
