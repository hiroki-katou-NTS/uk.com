package nts.uk.ctx.at.shared.app.command.ot.zerotime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.zerotime.ZeroTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class AddZeroTimeCommandHandler extends CommandHandler<AddZeroTimeCommand> {
	@Inject
	private ZeroTimeRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddZeroTimeCommand> context) {
		AddZeroTimeCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// convert to domain
		ZeroTime calcHoliday = command.toDomain(companyId);
		Optional<ZeroTime> optionalHoliday = this.repository.findByCId(companyId);

		if (optionalHoliday.isPresent()) {
			// update Holiday Addtime

			this.repository.update(calcHoliday);
		} else {
			// add Holiday Addtime
			this.repository.add(calcHoliday);
		}

	}

}
