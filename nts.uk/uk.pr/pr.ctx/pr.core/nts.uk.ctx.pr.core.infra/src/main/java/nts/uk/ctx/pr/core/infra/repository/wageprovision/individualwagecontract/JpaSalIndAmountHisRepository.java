package nts.uk.ctx.pr.core.infra.repository.wageprovision.individualwagecontract;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.*;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.individualwagecontract.QpbmtSalIndAmountHis;

@Stateless
public class JpaSalIndAmountHisRepository extends JpaRepository implements SalIndAmountHisRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.empId =:empId AND f.salIndAmountHisPk.salBonusCate =:salBonusCate AND f.salIndAmountHisPk.cateIndicator =:cateIndicator AND  f.salIndAmountHisPk.perValCode =:perValCode  ORDER BY f.periodStartYm DESC";
    private static final String SELECT_BY_KEY_STRING_DISPLAY = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId =:empId AND f.salIndAmountHisPk.salBonusCate = :salBonusCate AND f.salIndAmountHisPk.cateIndicator = :cateIndicator and f.periodStartYm <= :currentProcessYearMonth and f.periodEndYm >= :currentProcessYearMonth ORDER BY f.periodStartYm DESC";
    private static final String SELECT_BY_PER_VAL_CODE = SELECT_ALL_QUERY_STRING + " WHERE f.salIndAmountHisPk.perValCode =:perValCode AND  f.salIndAmountHisPk.empId IN :empIds AND f.salIndAmountHisPk.salBonusCate = :salBonusCate AND f.salIndAmountHisPk.cateIndicator = :cateIndicator and f.periodStartYm <= :standardYearMonth and f.periodEndYm >= :standardYearMonth ORDER BY f.salIndAmountHisPk.empId DESC";
    private static final String DELETE_BY_HISTORY_ID = "DELETE FROM QpbmtSalIndAmountHis f WHERE f.salIndAmountHisPk.historyId = :historyId";
    private static final String UPDATE_AMOUNT_BY_HISTORY_ID = "UPDATE QpbmtSalIndAmountHis f SET f.amountOfMoney = :amountOfMoney WHERE f.salIndAmountHisPk.historyId = :historyId";
    private static final String UPDATE_HISTORY_BY_HISTORY_ID = "UPDATE QpbmtSalIndAmountHis f SET f.periodStartYm = :periodStartYm, f.periodEndYm = :periodEndYm WHERE f.salIndAmountHisPk.historyId = :historyId";
    private static final String UPDATE_OLD_HISTORY_BY_HISTORY_ID = "UPDATE QpbmtSalIndAmountHis f SET f.periodEndYm = :periodEndYm WHERE f.salIndAmountHisPk.historyId = :historyId";

    @Override
    public List<SalaryIndividualAmountHistory> getSalIndAmountHis(String perValCode, String empId, int salBonusCate, int cateIndicator) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("empId", empId)
                .getList().stream().map(QpbmtSalIndAmountHis::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<SalaryIndividualAmountHistory> getSalIndAmountHisDisplay(String perValCode, String empId, int salBonusCate, int cateIndicator, int currentProcessYearMonth) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING_DISPLAY, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("empId", empId)
                .setParameter("currentProcessYearMonth", currentProcessYearMonth)
                .getList().stream().map(QpbmtSalIndAmountHis::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<PersonalAmount> getSalIndAmountHisByPerVal(String perValCode, int cateIndicator, int salBonusCate, int standardYearMonth, List<String> empIds) {
        return this.queryProxy().query(SELECT_BY_PER_VAL_CODE, QpbmtSalIndAmountHis.class)
                .setParameter("perValCode", perValCode)
                .setParameter("cateIndicator", cateIndicator)
                .setParameter("salBonusCate", salBonusCate)
                .setParameter("standardYearMonth", standardYearMonth)
                .setParameter("empIds", empIds)
                .getList().stream().map(QpbmtSalIndAmountHis::toDomainObject).collect(Collectors.toList());
    }

    @Override
    public void updateOldHistory(String historyId, int newEndMonthOfOldHistory) {
        this.getEntityManager().createQuery(UPDATE_OLD_HISTORY_BY_HISTORY_ID, QpbmtSalIndAmountHis.class)
                .setParameter("periodEndYm", newEndMonthOfOldHistory)
                .setParameter("historyId", historyId)
                .executeUpdate();
    }

    @Override
    public void updateHistory(SalIndAmountHis domain) {
        this.getEntityManager().createQuery(UPDATE_HISTORY_BY_HISTORY_ID, QpbmtSalIndAmountHis.class)
                .setParameter("periodStartYm", domain.getPeriod().stream().map(item -> item.getPeriodYearMonth().start().v()).findFirst().orElse(null))
                .setParameter("periodEndYm", domain.getPeriod().stream().map(item -> item.getPeriodYearMonth().end().v()).findFirst().orElse(null))
                .setParameter("historyId", domain.getPeriod().stream().map(GenericHistYMPeriod::getHistoryID).findFirst().orElse(null))
                .executeUpdate();
    }

    @Override
    public void remove(String historyId) {
        this.getEntityManager().createQuery(DELETE_BY_HISTORY_ID, QpbmtSalIndAmountHis.class)
                .setParameter("historyId", historyId)
                .executeUpdate();
    }

    @Override
    public void add(SalIndAmountHis domain1, SalIndAmount domain2) {
        this.commandProxy().insert(QpbmtSalIndAmountHis.toEntity(domain1, domain2));
    }

    @Override
    public void updateAmount(SalIndAmount domain) {
        this.getEntityManager().createQuery(UPDATE_AMOUNT_BY_HISTORY_ID, QpbmtSalIndAmountHis.class)
                .setParameter("amountOfMoney", domain.getAmountOfMoney().v())
                .setParameter("historyId", domain.getHistoryId())
                .executeUpdate();
    }
}
