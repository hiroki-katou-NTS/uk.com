package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class SalIndAmountByPerValCodeCommandHandler extends CommandHandler<SalIndAmountByPerValCodeCommand> {

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;


    @Override
    protected void handle(CommandHandlerContext<SalIndAmountByPerValCodeCommand> commandHandlerContext) {
        SalIndAmountByPerValCodeCommand salIndAmountByPerValCodeCommand=commandHandlerContext.getCommand();
        for(int i=0;i<salIndAmountByPerValCodeCommand.getEmpIDs().size();i++){

        }
    }
}
