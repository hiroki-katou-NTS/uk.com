package nts.uk.ctx.basic.app.command.organization.position;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.position.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.ctx.basic.dom.organization.position.PresenceCheckScopeSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class UpdateJobTitleCommandHandler extends CommandHandler<UpdateJobTitleCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateJobTitleCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		
		
		if(!JobCode.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()))){
			//throw err[ER026]
		}
		
		Position position = new Position(
				new JobName(context.getCommand().getJobName()),
				GeneralDate.localDate(context.getCommand().getEndDate()),
				new JobCode(context.getCommand().getJobCode()),
				new JobCode(context.getCommand().getJobOutCode()),
				GeneralDate.localDate(context.getCommand().getStartDate()),
				companyCode,
				new Memo(context.getCommand().getMemo()),
				new HiterarchyOrderCode(context.getCommand().getHiterarchyOrderCode()),
				PresenceCheckScopeSet.valueOf(Integer.toString(context.getCommand().getPresenceCheckScopeSet()))
				);
		positionRepository.update(position);
	}

}
