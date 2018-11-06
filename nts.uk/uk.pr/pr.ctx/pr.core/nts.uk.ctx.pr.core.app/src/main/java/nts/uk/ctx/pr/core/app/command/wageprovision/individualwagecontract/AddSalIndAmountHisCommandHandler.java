package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import java.util.List;

@Stateless
@Transactional
public class AddSalIndAmountHisCommandHandler extends CommandHandler<SalIndAmountHisCommand>
{
    
    @Inject
    private SalIndAmountHisRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalIndAmountHisCommand> context) {
        SalIndAmountHisCommand command = context.getCommand();

        //repository.add(new SalIndAmountHis(command.getPerValCode(), command.getEmpId(), command.getCateIndicator(), command.getPeriodStartYM(), command.getPeriodEndYM(), command.getSalBonusCate()));
    
    }
}
