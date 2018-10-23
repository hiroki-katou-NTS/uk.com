package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenHistoryService;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValue;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaValueRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddSalGenParaValueCommandHandler extends CommandHandler<SalGenParaYeahMonthValueCommand>
{
    
    @Inject
    private SalGenParaValueRepository repository;

    private final static int MODE_SCREEN_UPDATE = 0;
    private final static int MODE_SCREEN_ADD = 1;

    @Inject
    private SalGenHistoryService salGenHistoryService;

    @Override
    protected void handle(CommandHandlerContext<SalGenParaYeahMonthValueCommand> context) {
        SalGenParaYeahMonthValueCommand command = context.getCommand();
        if (command.getMSalGenParaValueCommand().getModeScreen() == MODE_SCREEN_ADD) {
            String newHistID = IdentifierUtil.randomUniqueId();
            salGenHistoryService.addEmpInsurBusBurRatio(newHistID, command.getParaNo(),
                    command.getStartTime(),
                    command.getEndTime(),
                    new SalGenParaValue(newHistID,
                            command.getMSalGenParaValueCommand().getSelection(),
                            command.getMSalGenParaValueCommand().getAvailableAtr(),
                            command.getMSalGenParaValueCommand().getNumValue(),
                            command.getMSalGenParaValueCommand().getCharValue(),
                            command.getMSalGenParaValueCommand().getTimeValue(),
                            command.getMSalGenParaValueCommand().getTargetAtr()),command.getModeHistory());


        }
        else{
            repository.update(new SalGenParaValue(command.getMSalGenParaValueCommand().getHistoryId(),
                    command.getMSalGenParaValueCommand().getSelection(),
                    command.getMSalGenParaValueCommand().getAvailableAtr(),
                    command.getMSalGenParaValueCommand().getNumValue(),
                    command.getMSalGenParaValueCommand().getCharValue(),
                    command.getMSalGenParaValueCommand().getTimeValue(),
                    command.getMSalGenParaValueCommand().getTargetAtr()));
        }

    }

}
