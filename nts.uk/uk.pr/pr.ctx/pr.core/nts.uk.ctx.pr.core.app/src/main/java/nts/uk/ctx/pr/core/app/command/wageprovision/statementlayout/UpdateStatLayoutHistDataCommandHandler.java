package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service.StatementLayoutSetService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateStatLayoutHistDataCommandHandler extends CommandHandler<StatementLayoutHistDataCommand> {
    @Inject
    private StatementLayoutRepository statementLayoutRepo;
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;
    @Inject
    private StatementLayoutSetService statementLayoutSetService;

    @Override
    protected void handle(CommandHandlerContext<StatementLayoutHistDataCommand> context) {
        StatementLayoutHistDataCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        StatementLayout statementLayout = new StatementLayout(cid, command.getStatementCode(), command.getStatementName());
        YearMonthHistoryItem yearMonthHist = new YearMonthHistoryItem(command.getHistoryId(),
                new YearMonthPeriod(new YearMonth(command.getStartMonth()), new YearMonth(command.getEndMonth())));
        StatementLayoutSet statementLayoutSet = command.getStatementLayoutSet().toDomain();

        statementLayoutRepo.update(statementLayout);
        if(command.isCheckCreate()) {
            statementLayoutHistRepo.add(cid, command.getStatementCode(), yearMonthHist);
            statementLayoutSetService.addStatementLayoutSet(statementLayoutSet);
        } else {
            statementLayoutHistRepo.update(cid, command.getStatementCode(), yearMonthHist);
            statementLayoutSetService.updateStatementLayoutSet(statementLayoutSet);
        }
    }
}
