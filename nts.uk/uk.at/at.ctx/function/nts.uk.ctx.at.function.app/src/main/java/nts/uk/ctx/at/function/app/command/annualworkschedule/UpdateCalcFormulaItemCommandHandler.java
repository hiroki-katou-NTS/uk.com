package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.CalcFormulaItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateCalcFormulaItemCommandHandler extends CommandHandler<CalcFormulaItemCommand>
{
	@Inject
	private CalcFormulaItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CalcFormulaItemCommand> context) {
		CalcFormulaItemCommand updateCommand = context.getCommand();
		repository.update(CalcFormulaItem.createFromJavaType(AppContexts.user().companyId(), updateCommand.getSetOutCd(),
															 updateCommand.getItemOutCd(),
															 updateCommand.getAttendanceItemId(),
															 updateCommand.getOperation()));
	}
}
