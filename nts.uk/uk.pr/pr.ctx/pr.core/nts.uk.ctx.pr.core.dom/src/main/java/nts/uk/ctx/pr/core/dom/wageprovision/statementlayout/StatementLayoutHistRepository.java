package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;
import java.util.List;

/**
* 明細書レイアウト履歴
*/
public interface StatementLayoutHistRepository
{

    List<StatementLayoutHist> getAllStatementLayoutHist();

    Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId);

    Optional<YearMonthHistoryItem> getLatestHistByCidAndCode(String cid, String code);

    Optional<YearMonthHistoryItem> getHistByCidAndCodeAndAfterDate(String cid, String code, int startYearMonth);

    void add(StatementLayoutHist domain);

    void update(StatementLayoutHist domain);

    void remove(String cid, int specCd, String histId);

    List<String> getStatemetnCode(String cid, String salaryCd, int startYearMonth);

}
