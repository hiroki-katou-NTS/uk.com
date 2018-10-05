package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.MonthlyTargetAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetPeriodCycleRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetValidityPeriodCycle;

@Stateless
@Transactional
public class RegisterValidityPeriodAndCycleSetCommandHandler extends CommandHandler<ValidityPeriodAndCycleSetCommand> {

	@Inject
	private SetPeriodCycleRepository setPeriodCycleRepository;

	@Override
	protected void handle(CommandHandlerContext<ValidityPeriodAndCycleSetCommand> context) {
		ValidityPeriodAndCycleSetCommand command = context.getCommand();

		setPeriodCycleRepository.register(new SetValidityPeriodCycle(command.getSalaryItemId(), command.getCycleSettingAtr(),
				command.isJanuary() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isFebruary() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isMarch() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isApril() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isMay() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isJune() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isJuly() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isAugust() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isSeptember() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isOctober() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isNovember() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.isDecember() ? MonthlyTargetAtr.COVERED.value : MonthlyTargetAtr.NOT_COVERED.value,
				command.getPeriodAtr(), command.getYearPeriodStart(), command.getYearPeriodEnd()));
	}

}
