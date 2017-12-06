package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Transactional
@Stateless
public class VerticalSettingCommandHandler extends CommandHandler<VerticalSettingCommand> {
	@Inject
	private VerticalSettingRepository repository;

	@Override
	protected void handle(CommandHandlerContext<VerticalSettingCommand> context) {
		VerticalSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		VerticalCalSet verticalCalSet = command.toDomain();
		verticalCalSet.validate();
		Optional<VerticalCalSet> data = this.repository.getVerticalCalSetByCode(companyId, command.getVerticalCalCd());
		
		if (data.isPresent()) {
			//throw new BusinessException("Msg_3");
			// update process
			repository.updateVerticalCalSet(verticalCalSet);
			
		} else {
			// insert process
			
			repository.addVerticalCalSet(verticalCalSet);
		}
	}
}
