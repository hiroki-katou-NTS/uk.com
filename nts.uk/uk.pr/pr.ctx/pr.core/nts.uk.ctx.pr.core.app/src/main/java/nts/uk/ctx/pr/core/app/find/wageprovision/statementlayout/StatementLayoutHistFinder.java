package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
/**
* 明細書レイアウト履歴: Finder
*/
public class StatementLayoutHistFinder
{
    @Inject
    private StatementLayoutHistRepository mStatementLayoutHistRepository;
    @Inject
    private StatementLayoutRepository mStatementLayoutRepository;

    public List<StatementLayoutHistDto> getAllStatementLayoutHist(int startYearMonth) {
        String cid = AppContexts.user().companyId();
        List<StatementLayoutHistDto> resulf = new ArrayList<StatementLayoutHistDto>();
        List<StatementLayoutHist> mStatementLayoutHist = mStatementLayoutHistRepository.getAllStatementLayoutHistByCid(cid, paramsPeriod);
        List<StatementLayout> mStatementLayout = mStatementLayoutRepository.getStatementCode(cid,startYearMonth);
        mStatementLayoutHist.forEach(item ->
                resulf.add(new StatementLayoutHistDto(item.getCid(), item.getStatementCode().v(), mStatementLayout.stream().filter(
                        x -> x.getStatementCode().v().equals(item.getStatementCode().v())
                ).findFirst().get().getStatementName().v(), item.getHistory().get(0).identifier(),
                        item.getHistory().get(0).start().v()
                        , item.getHistory().get(0).end().v()
                ))
        );
        return resulf;
    }

}
