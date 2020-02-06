package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.YearMonthHistoryItemCustom;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHist;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHistPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Stateless
public class JpaStatementLayoutHistRepository extends JpaRepository implements StatementLayoutHistRepository {
    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayoutHist f";

    private static final String SELECT_BY_CID_AND_CODE = SELECT_ALL_QUERY_STRING +
            " WHERE f.statementLayoutHistPk.cid = :cid AND f.statementLayoutHistPk.statementCd = :statementCd ";
    private static final String SELECT_BY_CID_AND_CODES_AND_YM = SELECT_ALL_QUERY_STRING +
            " WHERE f.statementLayoutHistPk.cid = :cid AND f.statementLayoutHistPk.statementCd IN :statementCds " +
            " AND f.startYearMonth <= :yearMonth AND f.endYearMonth >= :yearMonth";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING +
            " WHERE f.statementLayoutHistPk.histId = :histId AND f.statementLayoutHistPk.cid = :cid AND f.statementLayoutHistPk.statementCd = :statementCd";
    private static final String SELECT_BY_CID_AND_CODE_AND_AFTER_DATE = SELECT_BY_CID_AND_CODE +
            " AND f.startYearMonth >= :startYearMonth ";
    private static final String SELECT_LATEST_BY_CID_AND_CODE = SELECT_BY_CID_AND_CODE +
            " AND f.startYearMonth = (SELECT MAX(o.startYearMonth) FROM QpbmtStatementLayoutHist o " +
            " WHERE o.statementLayoutHistPk.cid = :cid AND o.statementLayoutHistPk.statementCd = :statementCd) ";
    private static final String ORDER_BY_START_DATE = " ORDER BY f.startYearMonth ASC";
    private static final String ORDER_BY_START_DATE_HISTORY_TREE = " ORDER BY f.startYearMonth DESC";

    private static final String SELECT_BY_CID_KEY_STRING = "SELECT f FROM QpbmtStatementLayoutHist f Where f.startYearMonth <= :startYearMonth AND f.endYearMonth >= :startYearMonth AND f.statementLayoutHistPk.cid = :cid";

    private static final String SELECT_BY_DATE = "SELECT f FROM QpbmtStatementLayoutHist f WHERE (:startYearMonth BETWEEN f.startYearMonth AND f.endYearMonth ) AND f.statementLayoutHistPk.cid = :cid ";

    @Override
    public List<StatementLayoutHist> getAllStatementLayoutHist() {
        return null;
    }

    @Override
    public Optional<YearMonthHistoryItem> getStatementLayoutHistById(String cid, String statementCd, String histId) {
        return this.queryProxy().query(SELECT_BY_ID, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid)
                .setParameter("statementCd", statementCd)
                .setParameter("histId", histId)
                .getSingle(c -> toYearMonthDomain(c));
    }

    @Override
    public List<StatementLayoutHist> getAllStatementLayoutHistByCid(String cid, int startYearMonth) {
        List<StatementLayoutHist> statementLayoutHist = toDomains(this.queryProxy().query(SELECT_BY_DATE, QpbmtStatementLayoutHist.class)
                .setParameter("startYearMonth", startYearMonth)
                .setParameter("cid", cid)
                .getList());
        return statementLayoutHist;
    }

    @Override
    public List<YearMonthHistoryItem> getAllHistByCidAndCode(String cid, String code) {
        return this.queryProxy().query(SELECT_BY_CID_AND_CODE + ORDER_BY_START_DATE_HISTORY_TREE, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid).setParameter("statementCd", code)
                .getList(c -> toYearMonthDomain(c));
    }

    @Override
    public StatementLayoutHist getLayoutHistByCidAndCode(String cid, String code) {
        return toDomain(this.queryProxy().query(SELECT_BY_CID_AND_CODE + ORDER_BY_START_DATE, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid).setParameter("statementCd", code)
                .getList());
    }

    @Override
    public List<StatementLayoutHist> getLayoutHistByCidAndCodesAndYM(String cid, List<String> codes, int yearMonth) {
        if (codes == null || codes.isEmpty()) return Collections.emptyList();
        List<StatementLayoutHist> statementLayoutHist = toDomains(this.queryProxy().query(SELECT_BY_CID_AND_CODES_AND_YM, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid)
                .setParameter("statementCds", codes)
                .setParameter("yearMonth", yearMonth)
                .getList());
        return statementLayoutHist;
    }

    @Override
    public Optional<YearMonthHistoryItem> getLatestHistByCidAndCode(String cid, String code) {
        return this.queryProxy().query(SELECT_LATEST_BY_CID_AND_CODE, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid).setParameter("statementCd", code)
                .getSingle(c -> toYearMonthDomain(c));
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
    public void add(String cid, String code, YearMonthHistoryItem domain, int layoutPattern) {
        this.commandProxy().insert(yearMonthToEntity(cid, code, domain, layoutPattern));
    }

    @Override
    public void update(String cid, String code, YearMonthHistoryItem domain) {
        Optional<QpbmtStatementLayoutHist> statementLayoutHistEntity = this.queryProxy().query(SELECT_BY_ID, QpbmtStatementLayoutHist.class)
                .setParameter("cid", cid)
                .setParameter("statementCd", code)
                .setParameter("histId", domain.identifier())
                .getSingle();
        if (statementLayoutHistEntity.isPresent()) {
            this.commandProxy().update(yearMonthToEntity(cid, code, domain, statementLayoutHistEntity.get().layoutPattern));
        }
    }

    @Override
    public void remove(String cid, String specCd, String histId) {
        this.commandProxy().remove(QpbmtStatementLayoutHist.class, new QpbmtStatementLayoutHistPk(cid, specCd, histId));
    }

    @Override
    public List<String> getStatemetnCode(String cid, String salaryCd, int startYearMonth) {
        return null;
    }

    private StatementLayoutHist toDomain(List<QpbmtStatementLayoutHist> entityList) {
        String cid = entityList.get(0).statementLayoutHistPk.cid;
        String code = entityList.get(0).statementLayoutHistPk.statementCd;
        List<YearMonthHistoryItem> yearMonthHistoryItemList = entityList.stream().map(
                i -> new YearMonthHistoryItemCustom(i.statementLayoutHistPk.histId,
                        new YearMonthPeriod(new YearMonth(i.startYearMonth), new YearMonth(i.endYearMonth)), i.layoutPattern))
                .collect(Collectors.toList());
        return new StatementLayoutHist(cid, code, yearMonthHistoryItemList);
    }

    private YearMonthHistoryItem toYearMonthDomain(QpbmtStatementLayoutHist entity) {
        return new YearMonthHistoryItemCustom(entity.statementLayoutHistPk.histId,
                new YearMonthPeriod(new YearMonth(entity.startYearMonth), new YearMonth(entity.endYearMonth)), entity.layoutPattern);
    }

    private QpbmtStatementLayoutHist yearMonthToEntity(String cid, String code, YearMonthHistoryItem domain, int layoutPattern) {
        return new QpbmtStatementLayoutHist(new QpbmtStatementLayoutHistPk(cid, code, domain.identifier()), domain.start().v(), domain.end().v(), layoutPattern);
    }

    private List<StatementLayoutHist> toDomains(List<QpbmtStatementLayoutHist> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<StatementLayoutHist>();
        }
        List<StatementLayoutHist> arrDataResulf = new ArrayList<StatementLayoutHist>();
        entities.forEach(item -> {
            List<YearMonthHistoryItem> history = new ArrayList<YearMonthHistoryItem>();
            history.add(new YearMonthHistoryItem(item.statementLayoutHistPk.histId, new YearMonthPeriod(
                    new YearMonth(item.startYearMonth),
                    new YearMonth(item.endYearMonth)
            )));
            arrDataResulf.add(new StatementLayoutHist(
                    item.statementLayoutHistPk.cid,
                    item.statementLayoutHistPk.statementCd,
                    history
            ));
        });
        return arrDataResulf;
    }

}
