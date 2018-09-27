/**
 * 9:20:26 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class DeleteMonthlyConCmdHandler extends CommandHandler<String> {
	
	@Inject
	private MonthlyCorrectConditionRepository monthlyCorrectConditionRepository;
	
	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String erCode = "U" + context.getCommand();
		this.monthlyCorrectConditionRepository.deleteMonthlyCorrectExtractCondition(erCode);
	}
}
