package nts.uk.ctx.basic.app.command.organization.position;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class UpdateHistoryCommandHandler extends CommandHandler<UpdateHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateHistoryCommand> context) {
		
	
		String companyCode = AppContexts.user().companyCode();
		String hitoryId = IdentifierUtil.randomUniqueId();
		JobHistory jobHistory = new JobHistory(companyCode,hitoryId,context.getCommand().getEndDate(),context.getCommand().getStartDate());
		positionRepository.updateHistory(jobHistory);
	}
		
//		if(!payClassificationRepository.isExisted(companyCode, 
//				new PayClassificationCode(context.getCommand().getPayClassificationCode()))){
//			//throw err[ER026]
//			throw new BusinessException(new RawErrorMessage("ER026"));
//
//		}
	}


