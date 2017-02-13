package nts.uk.ctx.basic.app.command.position;

import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassification;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationCode;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationName;
import nts.uk.ctx.basic.dom.organization.payclassification.PayClassificationRepository;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.JobName;
import nts.uk.ctx.basic.dom.organization.position.Position;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class UpdatePositionCommandHandler extends CommandHandler<UpdatePositionCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdatePositionCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		
		if(!JobCode.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()))){
			//throw err[ER026]
		}
		Position position = new Position(
				new JobName(context.getCommand().getJobName()),
				new JobCode(context.getCommand().getJobCode()),
				companyCode,
				new Memo(context.getCommand().getMemo())
				);
		positionRepository.update(position);
	}

}
