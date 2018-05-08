/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.DisplayRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OpOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class DisplayRestrictionCommandHandler extends CommandHandler<DisplayRestrictionCommand> {

	@Inject
	private OpOfDailyPerformance operationSettingRepo;

	@Override
	protected void handle(CommandHandlerContext<DisplayRestrictionCommand> context) {
		DisplayRestrictionCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// find from database
		OperationOfDailyPerformance opSetting = operationSettingRepo
				.find(new CompanyId(companyId));
		
		// update display restriction
		DisplayRestriction displayRestriction = new DisplayRestriction(command.getYearDisplayAtr(),
				command.getYearRemainingNumberCheck(), command.getSavingYearDisplayAtr(),
				command.getSavingYearRemainingNumberCheck(), command.getCompensatoryDisplayAtr(),
				command.getCompensatoryRemainingNumberCheck(), command.getSubstitutionDisplayAtr(),
				command.getSubstitutionRemainingNumberCheck());
		opSetting.updateDisplayRestriction(displayRestriction);
		operationSettingRepo.register(opSetting);
	}

}
