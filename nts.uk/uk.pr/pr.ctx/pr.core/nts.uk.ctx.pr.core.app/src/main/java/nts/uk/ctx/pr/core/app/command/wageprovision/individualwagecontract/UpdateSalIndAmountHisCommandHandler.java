package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
@Transactional
public class UpdateSalIndAmountHisCommandHandler extends CommandHandler<SalIndAmountHisCommand> {

    @Inject
    private SalIndAmountHisRepository repository;

    @Override
    protected void handle(CommandHandlerContext<SalIndAmountHisCommand> context) {
        SalIndAmountHisCommand command = context.getCommand();
        String perValCode = command.getPerValCode();
        String empId = command.getEmpId();
        int cateIndicator = command.getCateIndicator();
        int salBonusCate = command.getSalBonusCate();

        YearMonthPeriod period = command.getPeriod().getPeriodYearMonth();

        //SalIndAmountHis salIndAmountHis = new SalIndAmountHis(perValCode, empId, cateIndicator, period,salBonusCate  );

        //repository.update(salIndAmountHis);

    }
}
