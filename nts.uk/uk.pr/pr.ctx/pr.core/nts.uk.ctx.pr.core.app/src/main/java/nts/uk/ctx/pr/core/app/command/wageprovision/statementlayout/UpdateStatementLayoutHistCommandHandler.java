package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        String historyId = command.getHistoryId();
        Integer start = command.getStartMonth();
        Integer end = command.getEndMonth();

        List<YearMonthHistoryItem> yearMonthHistoryItemList = repository.getHistByCidAndCodeAndAfterDate(cid, code, start);

        if((start > end) || ((yearMonthHistoryItemList.size() > 0) && (yearMonthHistoryItemList.stream().anyMatch(history -> !history.identifier().equals(historyId))))) {
            throw new BusinessException("Msg_107");
        }

        YearMonthHistoryItem currentHistory = command.toYearMonthDomain();
        repository.update(cid, code, currentHistory);

        Optional<YearMonthHistoryItem> previousHistoryOptional = getPreviousHistory(cid, code, historyId);
        if(previousHistoryOptional.isPresent()) {
            YearMonthHistoryItem previousHistory = previousHistoryOptional.get();
            previousHistory.newSpan(previousHistory.start(), new YearMonth(start).previousMonth());

            repository.update(cid, code, previousHistory);
        }
    }

    private Optional<YearMonthHistoryItem> getPreviousHistory(String cid, String code, String historyId) {
        List<YearMonthHistoryItem> historyList = repository.getAllHistByCidAndCode(cid, code);
        Optional<YearMonthHistoryItem> currentHistory = historyList.stream().filter(his -> his.identifier().equals(historyId)).findFirst();

        if(!currentHistory.isPresent()) return Optional.empty();

        int index = historyList.indexOf(currentHistory.get());

        return (index > 0) ? Optional.of(historyList.get(index - 1)) : Optional.empty();
    }
}
