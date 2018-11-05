package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
* 明細書レイアウト履歴
*/
public interface StatementLayoutHistRepository
{

    List<StatementLayoutHist> getAllStatementLayoutHist();

    List<StatementLayoutHist> getAllStatementLayoutHistByCid(String cid,int startYearMonth paramsPeriod);

    Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId);

    void add(StatementLayoutHist domain);

    void update(StatementLayoutHist domain);

    void remove(String cid, int specCd, String histId);

    List<String> getStatemetnCode(String cid, String salaryCd, int startYearMonth);

}
