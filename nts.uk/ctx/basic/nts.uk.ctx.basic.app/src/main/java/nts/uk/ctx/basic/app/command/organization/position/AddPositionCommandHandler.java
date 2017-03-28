package nts.uk.ctx.basic.app.command.organization.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.JobTitle;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.dom.organization.position.PresenceCheckScopeSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class AddPositionCommandHandler extends CommandHandler<AddPositionCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<AddPositionCommand> context) {

		String companyCode = AppContexts.user().companyCode();
		// String hitoryId = IdentifierUtil.randomUniqueId();
		JobTitle jobTitle = new JobTitle(new JobName(context.getCommand().getJobName()),
				new JobCode(context.getCommand().getJobCode()), context.getCommand().getHistoryId(),
				new Memo(context.getCommand().getMemo()), companyCode);
		positionRepository.add(jobTitle);

	}

}
