package nts.uk.ctx.at.function.app.command.annualworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import javax.transaction.Transactional;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItemRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.CalcFormulaItem;

@Stateless
@Transactional
public class RemoveCalcFormulaItemCommandHandler extends CommandHandler<CalcFormulaItemCommand>
{
    
    @Inject
    private CalcFormulaItemRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<CalcFormulaItemCommand> context) {
        String cid = context.getCommand().getCid();
        repository.remove(cid);
    }
}
