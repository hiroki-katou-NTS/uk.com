package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterIndividualWageContractCommandHandler extends CommandHandler<SalIndAmountCommand> {

    @Inject
    SalIndAmountRepository salIndAmountRepository;

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;

    @Override
    protected void handle(CommandHandlerContext<SalIndAmountCommand> commandHandlerContext) {
        SalIndAmountCommand command = commandHandlerContext.getCommand();
        String historyId = command.getHistoryId();
        int amountOfMoney = command.getAmountOfMoney();
        SalIndAmount salIndAmount = new SalIndAmount(historyId, amountOfMoney);
        salIndAmountRepository.update(salIndAmount);

//
//        SalIndAmountHis salIndAmountHis = new SalIndAmountHis(perValCode, empId, cateIndicator, period, salBonusCate);
//        salIndAmountHisRepository.add(salIndAmountHis);
        salIndAmountRepository.add(salIndAmount);

    }
}
