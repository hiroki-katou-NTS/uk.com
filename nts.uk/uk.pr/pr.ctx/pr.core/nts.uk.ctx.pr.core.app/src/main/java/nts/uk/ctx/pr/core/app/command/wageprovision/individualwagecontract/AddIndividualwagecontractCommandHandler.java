package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

@Stateless
public class AddIndividualwagecontractCommandHandler extends CommandHandler<AddIndividualwagecontractCommand> {

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;

    @Override
    protected void handle(CommandHandlerContext<AddIndividualwagecontractCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        AddIndividualwagecontractCommand contractCommand = commandHandlerContext.getCommand();
        SalIndAmountHisCommand salIndAmountHisCommand = contractCommand.getSalIndAmountHisCommand();
        SalIndAmountCommand salIndAmountCommand = contractCommand.getSalIndAmountCommand();
        SalIndAmountHis salIndAmountHis = new SalIndAmountHis(
                cid,
                salIndAmountHisCommand.getPerValCode(),
                salIndAmountHisCommand.getEmpId(),
                salIndAmountHisCommand.getCateIndicator(),
                salIndAmountHisCommand.getYearMonthHistoryItem().stream().map(GenericHistYMPeriodCommand::fromCommandToDomain).collect(Collectors.toList()),
                salIndAmountHisCommand.getSalBonusCate()
        );
        SalIndAmount salIndAmount = new SalIndAmount(salIndAmountCommand.getHistoryId(), salIndAmountCommand.getAmountOfMoney());
        salIndAmountHisRepository.add(salIndAmountHis, salIndAmount);
        if (contractCommand.getOldHistoryId() != null && contractCommand.getNewEndMonthOfOldHistory() != 0) {
            this.salIndAmountHisRepository.updateOldHistory(contractCommand.getOldHistoryId(), contractCommand.getNewEndMonthOfOldHistory());
        }
    }
}
