package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters.SalGenParaValueCommand;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValueRepository;

@Stateless
@Transactional
public class AddSalGenParaValueCommandHandler extends CommandHandler<SalGenParaValueCommand>
{
    
    @Inject
    private SalGenParaValueRepository repository;

    private final static int MODE_SCREEN_UPDATE = 0;
    private final static int MODE_SCREEN_ADD = 1;

    @Override
    protected void handle(CommandHandlerContext<SalGenParaValueCommand> context) {
        SalGenParaValueCommand command = context.getCommand();
        if(command.getModeScreen()==MODE_SCREEN_ADD){
            repository.add(new SalGenParaValue(command.getHistoryId(), command.getSelection(), command.getAvailableAtr(), command.getNumValue(), command.getCharValue(), command.getTimeValue(), command.getTargetAtr()));
        }
        else{
            repository.update(new SalGenParaValue(command.getHistoryId(), command.getSelection(), command.getAvailableAtr(), command.getNumValue(), command.getCharValue(), command.getTimeValue(), command.getTargetAtr()));
        }

    }
}
