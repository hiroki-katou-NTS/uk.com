package nts.uk.ctx.pr.core.infra.repository.individualwagecontract;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.*;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHis;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountHisPk;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaSalIndAmountHisRepository extends JpaRepository implements SalIndAmountHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId AND f.salBonusCate = :salBonusCate AND f.cateIndicator = :cateIndicator ORDER BY f.periodStartYm DESC";
    private static final String SELECT_BY_KEY_STRING_DISPLAY = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId AND f.salBonusCate = :salBonusCate AND f.cateIndicator = :cateIndicator and f.periodStartYm <= :currentProcessYearMonth and f.periodEndYm >= :currentProcessYearMonth ORDER BY f.periodStartYm DESC";
    private static final String SELECT_ALL_SAL_IND_AMOUNT_HIS_AND_SAL_AMOUNT = " SELECT f.salIndAmountHisPk.empId, f.salIndAmountHisPk.historyId, f.periodStartYm , f.periodEndYm , s.amountOfMoney FROM QpbmtSalIndAmountHis f LEFT JOIN QpbmtSalIndAmount s ON s.salIndAmountPk.historyId = f.salIndAmountHisPk.historyId " +
            "WHERE f.salIndAmountHisPk.perValCode =:perValCode AND f.salIndAmountHisPk.empId IN :empId AND  f.cateIndicator =:cateIndicator AND f.salBonusCate=:salBonusCate ";

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
    public Optional<SalIndAmountHis> getSalIndAmountHisDisplay(String perValCode, String empId, int salBonusCate, int cateIndicator, int currentProcessYearMonth) {
        return this.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING_DISPLAY, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("empId", empId)
                .setParameter("currentProcessYearMonth", currentProcessYearMonth)
                .getList());
    }

    @Override
    public List<PersonalAmount> getSalIndAmountHisByPerVal(String perValCode, int cateIndicator, int salBonusCate, List<String> empIds) {
        List<Object[]> data = this.queryProxy().query(SELECT_ALL_SAL_IND_AMOUNT_HIS_AND_SAL_AMOUNT, Object[].class)
                .setParameter("perValCode", perValCode)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("empId", empIds)
                .getList();

        List<PersonalAmount> personalAmount = data.stream().map(x -> new PersonalAmount(x[0].toString(), x[1].toString(), "", "", (Integer) x[2], (Integer) x[3], (Long) x[4])).collect(Collectors.toList());

        return personalAmount;

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
