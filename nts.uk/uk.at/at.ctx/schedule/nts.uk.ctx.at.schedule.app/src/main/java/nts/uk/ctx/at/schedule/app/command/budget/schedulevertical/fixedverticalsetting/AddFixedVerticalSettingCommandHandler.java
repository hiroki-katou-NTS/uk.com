package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVertical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class AddFixedVerticalSettingCommandHandler extends CommandHandler<List<FixedVerticalSettingCommand>> {

	@Inject
	private FixedVerticalSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<List<FixedVerticalSettingCommand>> context) {
		List<FixedVerticalSettingCommand> fixedVerticalList = context.getCommand();
		String companyId = AppContexts.user().companyId();

		for (FixedVerticalSettingCommand item : fixedVerticalList) {
			FixedVertical fVertical = item.toDomain(companyId);
			fVertical.validate();
 			Optional<FixedVertical> optional = repository.find(companyId, item.getFixedItemAtr());
			
			if (optional.isPresent()) {
				repository.updateFixedVertical(fVertical);
			} else {
				repository.addFixedVertical(fVertical);
			}

		}
	}

}
