package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class StatementLayoutHistFinder {
    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;

    public Optional<YearMonthHistoryItem> getHistByCidAndCodeAndAfterDate(String code, int startYearMonth) {
        String cid = AppContexts.user().companyId();

        return statementLayoutHistRepo.getHistByCidAndCodeAndAfterDate(cid, code, startYearMonth);
    }
}
