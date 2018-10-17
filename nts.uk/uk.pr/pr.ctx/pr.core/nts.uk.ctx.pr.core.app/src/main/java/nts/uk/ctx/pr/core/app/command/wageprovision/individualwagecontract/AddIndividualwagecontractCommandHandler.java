package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AddIndividualwagecontractCommandHandler extends CommandHandler<AddIndividualwagecontractCommand> {

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;

    @Inject
    private SalIndAmountRepository salIndAmountRepository;

    @Override
    protected void handle(CommandHandlerContext<AddIndividualwagecontractCommand> commandHandlerContext) {
        AddIndividualwagecontractCommand contractCommand = commandHandlerContext.getCommand();
        SalIndAmountHisCommand salIndAmountHisCommand = contractCommand.getSalIndAmountHisCommand();
        SalIndAmountCommand salIndAmountCommand = contractCommand.getSalIndAmountCommand();
        Optional<SalIndAmountHis> salIndAmountHisDB = salIndAmountHisRepository.getSalIndAmountHis(
                salIndAmountHisCommand.getPerValCode(),
                salIndAmountHisCommand.getEmpId(),
                salIndAmountHisCommand.getSalBonusCate(),
                salIndAmountHisCommand.getCateIndicator()
        );
        if (salIndAmountHisDB.isPresent()) {
            SalIndAmountHis bo = salIndAmountHisDB.get();
            salIndAmountHisRepository.update(bo);
        } else {
            SalIndAmountHis salIndAmountHis = new SalIndAmountHis(
                    salIndAmountHisCommand.getPerValCode(),
                    salIndAmountHisCommand.getEmpId(),
                    salIndAmountHisCommand.getCateIndicator(),
                    salIndAmountHisCommand.getYearMonthHistoryItem().stream().map(item -> GenericHistYMPeriodCommand.fromCommandToDomain(item)).collect(Collectors.toList()),
                    salIndAmountHisCommand.getSalBonusCate()
            );
            salIndAmountHisRepository.add(salIndAmountHis);

        }
        String historyID = "01";
        SalIndAmount salIndAmount = new SalIndAmount(
                historyID,
                salIndAmountCommand.getAmountOfMoney()
        );
        salIndAmountRepository.add(salIndAmount);
        return;
    }
}
