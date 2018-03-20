/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.operationsetting.old;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.FunctionalRestriction;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OperationOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.old.OpOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Stateless
public class FunctionalRestrictionCommandHandler extends CommandHandler<FunctionalRestrictionCommand> {

	@Inject
	private OpOfDailyPerformance operationSettingRepo;

	@Override
	protected void handle(CommandHandlerContext<FunctionalRestrictionCommand> context) {
		FunctionalRestrictionCommand c = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// find from database
		OperationOfDailyPerformance opSetting = operationSettingRepo
				.find(new CompanyId(companyId));

		// update functional restriction
		FunctionalRestriction functionalRestriction = new FunctionalRestriction(c.isRegisteredTotalTimeCheer(),
				c.isCompleteDisplayOneMonth(), c.isUseWorkDetail(), c.isRegisterActualExceed(), c.isConfirmSubmitApp(),
				c.isUseInitialValueSet(), c.isStartAppScreen(), c.isDisplayConfirmMessage(), c.isUseSupervisorConfirm(),
				c.getSupervisorConfirmError(), c.isUseConfirmByYourself(), c.getYourselfConfirmError());
		opSetting.updateFunctionalRestriction(functionalRestriction);
		operationSettingRepo.register(opSetting);
	}

}
