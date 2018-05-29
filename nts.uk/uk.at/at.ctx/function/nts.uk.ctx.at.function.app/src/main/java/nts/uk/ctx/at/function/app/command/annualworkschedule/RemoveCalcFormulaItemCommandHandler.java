package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.CalcFormulaItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class RemoveCalcFormulaItemCommandHandler extends CommandHandler<CalcFormulaItemCommand>
{
	@Inject
	private CalcFormulaItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<CalcFormulaItemCommand> context) {
		String cid = AppContexts.user().companyId();
		String setOutCd = context.getCommand().getSetOutCd();
		String itemOutCd = context.getCommand().getItemOutCd();
		int attendanceItemId = context.getCommand().getAttendanceItemId();
		repository.remove(cid, setOutCd, itemOutCd, attendanceItemId);
	}
}
