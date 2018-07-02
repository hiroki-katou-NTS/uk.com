package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.otkcustomize;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.ContinuousHolCheckSet;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize.repo.ContinuousHolCheckSetRepo;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousVacationDays;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author tuannt-nws
 *
 */
@Stateless
public class ContinuousHolCheckSetSaveCommandHandler extends CommandHandler<ContinuousHolCheckSetSaveCommand> {

	/** The continous hol check set repo. */
	@Inject
	private ContinuousHolCheckSetRepo continousHolCheckSetRepo;

	@Override
	protected void handle(CommandHandlerContext<ContinuousHolCheckSetSaveCommand> context) {
		ContinuousHolCheckSetSaveCommand command = context.getCommand();
		// convert to domain
		ContinuousHolCheckSet domain = new ContinuousHolCheckSet(AppContexts.user().companyId(),
				command.getTargetWorkType().stream().map(e -> new WorkTypeCode(e)).collect(Collectors.toList()),
				command.getIgnoreWorkType().stream().map(e -> new WorkTypeCode(e)).collect(Collectors.toList()),
				command.isUseAtr(), command.getDisplayMessage(), new ContinuousVacationDays(command.getMaxContinuousDays()));
		if (command.isUpdateMode()) {
			this.continousHolCheckSetRepo.update(domain);
		} else {
			this.continousHolCheckSetRepo.insert(domain);
		}
	}

}
