package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
@Stateless
public class AddIndividualwagecontractCommandHandler extends CommandHandler<AddIndividualwagecontractCommand> {

    @Inject
    SalIndAmountHisRepository salIndAmountHisRepository;

    @Override
    protected void handle(CommandHandlerContext<AddIndividualwagecontractCommand> commandHandlerContext) {
        AddIndividualwagecontractCommand contractCommand = commandHandlerContext.getCommand();
        SalIndAmountHisCommand salIndAmountHisCommand = contractCommand.getSalIndAmountHisCommand();
//        Optional<SalIndAmountHis> salIndAmountHisDB = salIndAmountHisRepository.getSalIndAmountHisById(salIndAmountHisCommand.getPeriod().getHistoryID(), salIndAmountHisCommand.getPerValCode(), salIndAmountHisCommand.getEmpId());
//        if (salIndAmountHisDB == null) {
//        SalIndAmountHis salIndAmountHis = new SalIndAmountHis(salIndAmountHisCommand.getPerValCode(), salIndAmountHisCommand.getEmpId(), salIndAmountHisCommand.getCateIndicator(), salIndAmountHisCommand.getPeriod(), salIndAmountHisCommand.getSalBonusCate());
//        salIndAmountHisRepository.add(salIndAmountHis);
    }

}
