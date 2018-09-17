package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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

		setPeriodCycleRepository.remove(command.getSalaryItemId());
		setPeriodCycleRepository.add(new SetValidityPeriodCycle(command.getSalaryItemId(), command.getCycleSettingAtr(),
				command.getJanuary(), command.getFebruary(), command.getMarch(), command.getApril(), command.getMay(),
				command.getJune(), command.getJuly(), command.getAugust(), command.getSeptember(), command.getOctober(),
				command.getNovember(), command.getDecember(), command.getPeriodAtr(), command.getYearPeriodStart(),
				command.getYearPeriodEnd()));
	}

}
