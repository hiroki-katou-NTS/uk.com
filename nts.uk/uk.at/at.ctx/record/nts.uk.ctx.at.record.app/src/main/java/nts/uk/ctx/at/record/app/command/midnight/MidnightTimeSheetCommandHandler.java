package nts.uk.ctx.at.record.app.command.midnight;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight.MidnightTimeSheetRepo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author yennh
 *
 */
@Stateless
public class MidnightTimeSheetCommandHandler extends CommandHandler<MidnightTimeSheetCommand> {
	@Inject
	private MidnightTimeSheetRepo repo;

	@Override
	protected void handle(CommandHandlerContext<MidnightTimeSheetCommand> context) {
		MidnightTimeSheetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		MidNightTimeSheet midNightTimeSheet = command.toDomain(companyId);
		midNightTimeSheet.validate();
		Optional<MidNightTimeSheet> optional = this.repo.findByCId(companyId);

		if (optional.isPresent()) {
			// update midnight time sheet
			this.repo.update(midNightTimeSheet);
		} else {
			// add midnight time sheet
			this.repo.add(midNightTimeSheet);
		};
	}

}
