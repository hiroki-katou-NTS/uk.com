package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateStatementLayoutHistCommandHandler extends CommandHandler<StatementLayoutHistCommand> {
    @Inject
    private StatementLayoutHistRepository repository;

    @Override
    protected void handle(CommandHandlerContext<StatementLayoutHistCommand> context) {
        StatementLayoutHistCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        String code = command.getStatementCode();

        StatementLayoutHist statementLayoutHist = repository.getLayoutHistByCidAndCode(cid, code);
        YearMonthHistoryItem currentHistory = command.toYearMonthDomain();

        statementLayoutHist.changeSpan(currentHistory, currentHistory.span());
        repository.update(cid, code, currentHistory);

        if(statementLayoutHist.immediatelyBefore(currentHistory).isPresent()) {
            YearMonthHistoryItem beforeHistory = statementLayoutHist.immediatelyBefore(currentHistory).get();
            repository.update(cid, code, beforeHistory);
        }

    }
}
