package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;

@Stateless
public class DeleteFixedConWorkRecordCmdHandler extends CommandHandler<DeleteFixedConWorkRecordCmd> {

	@Inject
	private FixedConditionWorkRecordRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteFixedConWorkRecordCmd> context) {
		DeleteFixedConWorkRecordCmd command = context.getCommand();
		Optional<FixedConditionWorkRecord> checkData = repo.getFixedConWRByCode(command.getDailyAlarmConID(),command.getFixConWorkRecordNo());
		if(checkData.isPresent()) {
			repo.deleteFixedConWorkRecord(command.getDailyAlarmConID());
		} else {
			throw new BusinessException("Msg_3");
		}
	}

}
