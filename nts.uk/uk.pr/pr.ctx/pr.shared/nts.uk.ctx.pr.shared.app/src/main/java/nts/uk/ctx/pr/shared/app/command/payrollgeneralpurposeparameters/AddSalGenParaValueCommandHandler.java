package nts.uk.ctx.pr.shared.app.command.payrollgeneralpurposeparameters;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddSalGenParaValueCommandHandler extends CommandHandler<SalGenParaYeahMonthValueCommand>
{
    
    @Inject
    private SalGenParaYMHistRepository repository;
    @Inject
    private SalGenParaDateHistRepository mSalGenParaDateHistRepository;

    private final static int MODE_SCREEN_ADD = 1;
    private final static int MODE_HISTORY_YEARMONTH = 1;
    @Inject
    private SalGenHistoryService salGenHistoryService;

    @Override
    protected void handle(CommandHandlerContext<SalGenParaYeahMonthValueCommand> context) {
        SalGenParaYeahMonthValueCommand command = context.getCommand();
        if (command.getMSalGenParaValueCommand().getModeScreen() == MODE_SCREEN_ADD) {
            String newHistID = IdentifierUtil.randomUniqueId();
            salGenHistoryService.addSalGenParam(newHistID, command.getParaNo(),
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
            if(command.getModeHistory() == MODE_HISTORY_YEARMONTH){
                repository.updateSalGenParaValue(command.getParaNo(),new SalGenParaValue(command.getMSalGenParaValueCommand().getHistoryId(),
                        command.getMSalGenParaValueCommand().getSelection(),
                        command.getMSalGenParaValueCommand().getAvailableAtr(),
                        command.getMSalGenParaValueCommand().getNumValue(),
                        command.getMSalGenParaValueCommand().getCharValue(),
                        command.getMSalGenParaValueCommand().getTimeValue(),
                        command.getMSalGenParaValueCommand().getTargetAtr()));
            }
            else{
                mSalGenParaDateHistRepository.updateSalGenParaValue(command.getParaNo(),new SalGenParaValue(command.getMSalGenParaValueCommand().getHistoryId(),
                        command.getMSalGenParaValueCommand().getSelection(),
                        command.getMSalGenParaValueCommand().getAvailableAtr(),
                        command.getMSalGenParaValueCommand().getNumValue(),
                        command.getMSalGenParaValueCommand().getCharValue(),
                        command.getMSalGenParaValueCommand().getTimeValue(),
                        command.getMSalGenParaValueCommand().getTargetAtr()));
            }

        }

    }

}
