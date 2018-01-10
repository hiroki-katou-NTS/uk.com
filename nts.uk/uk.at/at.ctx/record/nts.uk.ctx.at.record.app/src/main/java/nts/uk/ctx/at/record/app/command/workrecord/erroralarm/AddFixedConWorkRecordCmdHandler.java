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
public class AddFixedConWorkRecordCmdHandler extends CommandHandler<FixedConditionWorkRecordCmd> {

	@Inject
	private FixedConditionWorkRecordRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<FixedConditionWorkRecordCmd> context) {
		FixedConditionWorkRecordCmd fixedConditionWorkRecordCmd = context.getCommand();
		FixedConditionWorkRecord fixedConditionWorkRecord =fixedConditionWorkRecordCmd.fromDomain();
		Optional<FixedConditionWorkRecord> checkData = repo.getFixedConWRByCode(
				fixedConditionWorkRecord.getDailyAlarmConID(), fixedConditionWorkRecord.getFixConWorkRecordNo().value);
		if(checkData.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			repo.addFixedConWorkRecord(fixedConditionWorkRecord);
		}
		
	}

}
