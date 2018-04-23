package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItemRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;

@Stateless
@Transactional
public class UpdateCalcFormulaItemCommandHandler extends CommandHandler<CalcFormulaItemCommand> {
	
	@Inject
	private CalcFormulaItemRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<CalcFormulaItemCommand> context) {
		CalcFormulaItemCommand updateCommand = context.getCommand();
		repository.update(new CalcFormulaItem(updateCommand.getCid(), updateCommand.getSetOutCd(), updateCommand.getItemOutCd(), updateCommand.getAttendanceItemId(), updateCommand.getOperation()));
	}
}
