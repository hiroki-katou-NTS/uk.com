package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateMonthlyRecordWorkTypeCmdHandler  extends CommandHandler<MonthlyRecordWorkTypeCmd> {

	@Inject
	private MonthlyRecordWorkTypeRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<MonthlyRecordWorkTypeCmd> context) {
		String companyID = AppContexts.user().companyId();
		MonthlyRecordWorkTypeCmd command = context.getCommand();
		command.setCompanyID(companyID);
		Optional<MonthlyRecordWorkType> data = repo.getMonthlyRecordWorkTypeByCode(companyID, command.getBusinessTypeCode());
		if(!data.isPresent()) {
			repo.addMonthlyRecordWorkType(MonthlyRecordWorkTypeCmd.fromCommand(command));
		}else {
			repo.updateMonthlyRecordWorkType(MonthlyRecordWorkTypeCmd.fromCommand(command));
			
		}
		
	}

}
