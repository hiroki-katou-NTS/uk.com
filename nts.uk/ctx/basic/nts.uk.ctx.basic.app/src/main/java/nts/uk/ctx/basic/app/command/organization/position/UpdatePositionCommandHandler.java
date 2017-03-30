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
public class UpdatePositionCommandHandler extends CommandHandler<UpdatePositionCommand>{

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdatePositionCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		String hitoryId = IdentifierUtil.randomUniqueId();
		
//		UpdatePositionCommand command = context.getCommand();
//		JobTitle jobTitle = new JobTitle(new JobName(command.getJobName()),
//				new JobCode(command.getJobCode()),
//				new JobCode(command.getJobOutCode()),
//				command.getHistoryId(), companyCode, 
//				new Memo(command.getMemo()) ,
//				new HiterarchyOrderCode(command.getHiterarchyOrderCode()),
//				PresenceCheckScopeSet.SetForEachRole);
//		
//		positionRepository.update(jobTitle);
		
	}
	
//	@Override
//	protected void handle(CommandHandlerContext<UpdatePositionCommand> context) {
//		UpdatePositionCommand update = context.getCommand();
//		JobTitle position = update.toDomain();
//		position.validate();
//		this.positionRepo.update(position);
//		
//	}

	
}
