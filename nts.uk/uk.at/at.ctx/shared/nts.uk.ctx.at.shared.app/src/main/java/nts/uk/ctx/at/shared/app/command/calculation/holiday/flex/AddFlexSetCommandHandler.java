package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;

/**
 * @author phongtq
 * The class Flex Set Add Flex Set Command Handler
 */
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddFlexSetCommandHandler extends CommandHandlerWithResult<AddFlexSetCommand, List<String>> {
	@Inject
	private FlexSetRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddFlexSetCommand> context) {
		AddFlexSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// convert to domain
		FlexSet flexSet = command.toDomain(companyId);
		flexSet.validate();
		Optional<FlexSet> optionalHoliday = this.repository.findByCId(companyId);

		if (optionalHoliday.isPresent()) {
			// update Holiday Addtime
			this.repository.update(flexSet);
		}

		// add Holiday Addtime
		this.repository.add(flexSet);
		return null;
	}
}
