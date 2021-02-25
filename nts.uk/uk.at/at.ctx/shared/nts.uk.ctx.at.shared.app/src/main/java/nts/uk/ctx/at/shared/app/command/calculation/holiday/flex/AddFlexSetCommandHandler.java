package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddFlexSetCommandHandler extends CommandHandler<AddFlexSetCommand> {
	@Inject
	private FlexSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddFlexSetCommand> context) {
		AddFlexSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// convert to domain
		FlexSet flexSet = command.toDomain(companyId);
		flexSet.validate();
		Optional<FlexSet> optionalHoliday = this.repository.findByCId(companyId);

		if (optionalHoliday.isPresent()) {
			// update Holiday Addtime
			this.repository.update(flexSet);
		}else {
			// add Holiday Addtime
			this.repository.add(flexSet);
			};
	}
}
