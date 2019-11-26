package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.AmountOfMoney;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class SalIndAmountUpdateCommandHandler extends CommandHandler<SalIndAmountUpdateAllCommand> {

    @Inject
    private SalIndAmountHisRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SalIndAmountUpdateAllCommand> commandHandlerContext) {
        SalIndAmountUpdateAllCommand command = commandHandlerContext.getCommand();
        command.getSalIndAmountUpdateCommandList().forEach(e -> {
            repository.updateAmount(new SalIndAmount(e.getHistoryId(), e.getAmount()));
        });

    }
}
