package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHist;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaStatementLayoutHistRepository extends JpaRepository implements StatementLayoutHistRepository {
    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayoutHist f";
    private static final String SELECT_BY_CID_AND_CODE = SELECT_ALL_QUERY_STRING +
            " WHERE f.statementLayoutHistPk.cid = :cid AND f.statementLayoutHistPk.statementCd = :statementCd ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING +
            " WHERE f.statementLayoutHistPk.histId = :histId ";
    private static final String SELECT_BY_CID_AND_CODE_AND_AFTER_DATE = SELECT_BY_CID_AND_CODE +
            " AND f.startYearMonth > :startYearMonth ";
    private static final String SELECT_LATEST_BY_CID_AND_CODE = SELECT_BY_CID_AND_CODE +
            " AND f.startYearMonth = (SELECT MAX(o.startYearMonth) FROM QpbmtStatementLayoutHist o " +
            " WHERE o.statementLayoutHistPk.cid = :cid AND o.statementLayoutHistPk.statementCd = :statementCd) ";

    @Override
    public List<StatementLayoutHist> getAllStatementLayoutHist() {
        return null;
    }

    @Override
    public List<YearMonthHistoryItem> getStatementLayoutHistById(String histId) {
        return this.queryProxy().query(SELECT_BY_ID, QpbmtStatementLayoutHist.class)
                .setParameter("histId", histId)
                .getList(c -> toYearMonthDomain(c));
    }

    @Override
    public List<YearMonthHistoryItem> getLatestHistByCidAndCode(String cid, String code) {
        return this.queryProxy().query(SELECT_LATEST_BY_CID_AND_CODE, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid).setParameter("statementCd", code)
                .getList(c -> toYearMonthDomain(c));
    }

    @Override
    public List<YearMonthHistoryItem> getHistByCidAndCodeAndAfterDate(String cid, String code, int startYearMonth) {
        return this.queryProxy().query(SELECT_BY_CID_AND_CODE_AND_AFTER_DATE, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid).setParameter("statementCd", code).setParameter("startYearMonth", startYearMonth)
                .getList(c -> toYearMonthDomain(c));
    }

    @Override
    public void add(StatementLayoutHist domain) {

    }

    @Override
    public void update(StatementLayoutHist domain) {

    }

    @Override
    public void remove(String cid, int specCd, String histId) {

    }

    @Override
    public List<String> getStatemetnCode(String cid, String salaryCd, int startYearMonth) {
        return null;
    }

    private YearMonthHistoryItem toYearMonthDomain(QpbmtStatementLayoutHist entity) {
        return new YearMonthHistoryItem(entity.statementLayoutHistPk.histId,
                new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth)));
    }
}
