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
public class DeleteWorkRecordExtraConCommandHandler extends CommandHandler<DeleteWorkRecordExtraConCommand> {

	@Inject
	private WorkRecordExtraConRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteWorkRecordExtraConCommand> context) {
		// TODO Auto-generated method stub
		Optional<WorkRecordExtractingCondition> checkData = repo.getWorkRecordExtraConById(
				context.getCommand().getErrorAlarmCheckID());
		if(checkData.isPresent()) {
			repo.deleteWorkRecordExtraCon(context.getCommand().getErrorAlarmCheckID());
		} else {
			throw new BusinessException("Msg_3");
		}	
	}

}
