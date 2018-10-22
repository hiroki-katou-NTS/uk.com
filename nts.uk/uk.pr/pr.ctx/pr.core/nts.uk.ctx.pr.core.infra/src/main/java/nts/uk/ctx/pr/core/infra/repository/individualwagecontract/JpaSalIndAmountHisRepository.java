package nts.uk.ctx.pr.core.infra.repository.individualwagecontract;

import java.util.ArrayList;
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
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountHisPk.historyId =:historyId AND  f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId ";


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
    public Optional<SalIndAmountHis> getSalIndAmountHisById(String historyId, String perValCode, String empId) {
        return this.toDomain(this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountHis.class)
                .setParameter("historyId", historyId)
                .setParameter("perValCode", perValCode)
                .setParameter("empId", empId)
                .getList());
    }

    private static final String SELECT_BY_KEY_PEL_VAL = "SELECT f.salIndAmountHisPk.historyId ,f.periodStartYm ,f.periodEndYm  WHERE f.salIndAmountHisPk.perValCode =:perValCode AND WHERE f.salIndAmountHisPk.empId =:empId AND  f.cateIndicator =:cateIndicator AND f.salBonusCate=:salBonusCate ";

    @Override
    public List<GenericHistYMPeriod> getSalIndAmountHisByPerVal(String perValCode, int cateIndicator, int salBonusCate, String empId) {
       return this.toDomains(
                this.queryProxy().query(SELECT_BY_KEY_PEL_VAL, QpbmtSalIndAmountHis.class)
                        .setParameter("perValCode", perValCode)
                        .setParameter("cateIndicator", cateIndicator)
                        .setParameter("salBonusCate", salBonusCate)
                        .setParameter("empId", empId)
                        .getList()
        );
    }

    private List<GenericHistYMPeriod> toDomains(List<QpbmtSalIndAmountHis> entitys) {

        if (entitys.size() > 0 && entitys != null) {
            List<GenericHistYMPeriod> genericHistYMPeriods = new ArrayList<>();
            for (int i = 0; i < entitys.size(); i++) {
                genericHistYMPeriods.add(new GenericHistYMPeriod(
                        entitys.get(i).salIndAmountHisPk.historyId,
                        entitys.get(i).periodStartYm,
                        entitys.get(i).periodEndYm
                ));
            }
            return genericHistYMPeriods;
        } else
            return null;
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


    private Optional<SalIndAmountHis> toDomainListEmpID(List<QpbmtSalIndAmountHis> entity) {
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


    public List<QpbmtSalIndAmountHis> getSalIndAmountHisByPerVal1(String perValCode, int cateIndicator, int salBonusCate) {
        return this.queryProxy().query(SELECT_BY_KEY_PEL_VAL, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("salBonusCate", salBonusCate)
                .getList();
    }


}
