package nts.uk.ctx.pr.core.infra.repository.individualwagecontract;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHisRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHis;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHisPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaSalIndAmountHisRepository extends JpaRepository implements SalIndAmountHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId AND f.salBonusCate = :salBonusCate AND f.cateIndicator = :cateIndicator ORDER BY f.periodStartYm DESC";


    private Optional<SalIndAmountHis> toDomain(List<QpbmtSalIndAmountHis> entity) {
        if (entity.size() > 0) {
            String perValCode = entity.get(0).salIndAmountHisPk.perValCode;
            String empId = entity.get(0).salIndAmountHisPk.empId;
            int cateIndicator = entity.get(0).cateIndicator;
            int salBonusCate = entity.get(0).salBonusCate;
            List<GenericHistYMPeriod> history = entity.stream()
                    .map(item -> new GenericHistYMPeriod(item.salIndAmountHisPk.historyId,
                            new YearMonthPeriod(new YearMonth(item.periodStartYm), new YearMonth(item.periodEndYm))))
                    .collect(Collectors.toList());
            return Optional.of(new SalIndAmountHis(perValCode, empId, cateIndicator, history, salBonusCate));
        } else {
            return Optional.empty();
        }
    }


    @Override
    public List<SalIndAmountHis> getAllSalIndAmountHis() {
        return null;
    }

    @Override
    public Optional<SalIndAmountHis> getSalIndAmountHis(String perValCode, String empId, int salBonusCate, int cateIndicator) {
        return this.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("empId", empId)
                .getList());
    }

    @Override
    public void add(SalIndAmountHis domain) {
        this.commandProxy().insert(QpbmtSalIndAmountHis.toEntity(domain));
    }

    @Override
    public void update(SalIndAmountHis domain) {
        this.commandProxy().update(QpbmtSalIndAmountHis.toEntity(domain));
    }

    @Override
    public void remove(String historyId, String perValCode, String empId) {
        this.commandProxy().remove(QpbmtSalIndAmountHis.class, new QpbmtSalIndAmountHisPk(historyId, perValCode, empId));
    }
}
