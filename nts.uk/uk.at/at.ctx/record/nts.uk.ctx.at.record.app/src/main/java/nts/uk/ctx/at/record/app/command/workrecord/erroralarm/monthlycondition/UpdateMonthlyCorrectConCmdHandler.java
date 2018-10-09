/**
 * 9:19:49 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectExtractCondition;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class UpdateMonthlyCorrectConCmdHandler extends CommandHandler<UpdateMonthlyCorrectConCmd> {

	@Inject
	private MonthlyCorrectConditionRepository monthlyCorrectConditionRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateMonthlyCorrectConCmd> context) {
		UpdateMonthlyCorrectConCmd command = context.getCommand();
		command.setCode("U" + command.getCode());
		if(this.monthlyCorrectConditionRepository.findMonthlyConditionByCode(command.getCode()).isPresent()){
			if (command.getNewMode() == 1)
				throw new BusinessException("Msg_3");
			MonthlyCorrectExtractCondition domainMonthly = this.monthlyCorrectConditionRepository.updateMonthlyCorrectExtractCondition(command.toMonthlyCorrectExtractCondition());
			this.monthlyCorrectConditionRepository.removeTimeItemCheckMonthly(domainMonthly.getErrorAlarmCheckID());
			this.monthlyCorrectConditionRepository.updateTimeItemCheckMonthly(command.toTimeItemCheckMonthly(domainMonthly.getErrorAlarmCheckID()));
		} else {
			command.setCompanyId(AppContexts.user().companyId());
			MonthlyCorrectExtractCondition domainMonthly = this.monthlyCorrectConditionRepository.createMonthlyCorrectExtractCondition(command.toMonthlyCorrectExtractCondition());
			this.monthlyCorrectConditionRepository.createTimeItemCheckMonthly(command.toTimeItemCheckMonthly(domainMonthly.getErrorAlarmCheckID()));
		}
	}
}
