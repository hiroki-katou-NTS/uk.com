package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtStatementLayoutHist;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaStatementLayoutHistRepository extends JpaRepository implements StatementLayoutHistRepository {
    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtStatementLayoutHist f";
    private static final String SELECT_BY_CID_KEY_STRING = "SELECT f FROM QpbmtStatementLayoutHist f Where f.startYearMonth <= :startYearMonth AND f.endYearMonth >= :startYearMonth AND f.statementLayoutHistPk.cid = :cid";



    @Override
    public List<StatementLayoutHist> getAllStatementLayoutHist() {
        return null;
    }

    @Override
    public  List<StatementLayoutHist> getAllStatementLayoutHistByCid(String cid,int startYearMonth) {
        List<StatementLayoutHist> statementLayoutHist = toDomain(this.queryProxy().query(SELECT_BY_CID_KEY_STRING, QpbmtStatementLayoutHist.class)
                .setParameter("startYearMonth", startYearMonth)
                .setParameter("cid", cid)
                .getList());
        return statementLayoutHist;
    }

    @Override
    public Optional<StatementLayoutHist> getStatementLayoutHistById(String cid, int specCd, String histId) {
        return Optional.empty();
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


    private List<StatementLayoutHist> toDomain(List<QpbmtStatementLayoutHist> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<StatementLayoutHist>();
        }
        List<StatementLayoutHist> arrDataResulf = new ArrayList<StatementLayoutHist>();
        entities.forEach(item -> {
            List<YearMonthHistoryItem> history = new ArrayList<YearMonthHistoryItem>();
            history.add(new YearMonthHistoryItem(item.statementLayoutHistPk.histId,new YearMonthPeriod(
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
