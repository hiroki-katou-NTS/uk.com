package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtraConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkRecordExtractingCondition;

@Stateless
public class AddWorkRecordExtraConCommandHandler  extends CommandHandler<WorkRecordExtraConCommand> {

	@Inject
	private WorkRecordExtraConRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<WorkRecordExtraConCommand> context) {
		WorkRecordExtraConCommand workRecordExtraConCommand = context.getCommand();
		WorkRecordExtractingCondition workRecordExtractingCondition  = workRecordExtraConCommand.fromDomain();
		Optional<WorkRecordExtractingCondition> checkData = repo.getWorkRecordExtraConById(
				workRecordExtractingCondition.getErrorAlarmCheckID(),
				workRecordExtractingCondition.getCheckItem().value);
		if(checkData.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			repo.addWorkRecordExtraCon(workRecordExtractingCondition);
		}		
		
		
	}

}
