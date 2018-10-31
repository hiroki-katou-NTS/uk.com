package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateSalIndAmountHisCommandHandler extends CommandHandler<SalIndAmountHisCommand> {

    @Inject
    private SalIndAmountHisRepository salIndAmountHisRepository;

    @Override
    protected void handle(CommandHandlerContext<SalIndAmountHisCommand> context) {
        SalIndAmountHisCommand command = context.getCommand();
        String perValCode = command.getPerValCode();
        String empId = command.getEmpId();
        int cateIndicator = command.getCateIndicator();
        int salBonusCate = command.getSalBonusCate();
        List<GenericHistYMPeriod> period = command.getYearMonthHistoryItem().stream().map(item -> new GenericHistYMPeriod(item.historyId, item.startMonth, item.endMonth)).collect(Collectors.toList());
        SalIndAmountHis salIndAmountHis = new SalIndAmountHis(perValCode, empId, cateIndicator, period, salBonusCate);
        salIndAmountHisRepository.update(salIndAmountHis);
    }
}
