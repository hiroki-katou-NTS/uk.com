package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class SalIndAmountUpdateCommandHandler extends CommandHandler<SalIndAmountUpdateAllCommand> {

    @Inject
    private SalIndAmountRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SalIndAmountUpdateAllCommand> commandHandlerContext) {
        SalIndAmountUpdateAllCommand command = commandHandlerContext.getCommand();
        repository.updateAll(command.getSalIndAmountUpdateCommandList().stream().map(v->new SalIndAmount(v.getHistoryID(), Long.parseLong(v.getAmountOfMoney()))).collect(Collectors.toList()));
    }
}
