package nts.uk.ctx.pr.core.infra.repository.wageprovision.empsalunitprice;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.*;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.empsalunitprice.QpbmtEmpSalPriHis;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.empsalunitprice.QpbmtEmpSalPriHisPk;
import nts.uk.shr.com.history.GeneralHistoryItem;

@Stateless
public class JpaEmployeeSalaryUnitPriceHistoryRepository extends JpaRepository implements EmployeeSalaryUnitPriceHistoryRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpSalPriHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.empSalPriHisPk.personalUnitPriceCode = :personalUnitPriceCode AND f.empSalPriHisPk.employeeId IN :employeeId AND f.startYearMonth <= :yearMonthFilter AND f.endYearMonth >= :yearMonthFilter";
    private static final String SELECT_BY_CODE_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.empSalPriHisPk.personalUnitPriceCode = :personalUnitPriceCode AND f.empSalPriHisPk.employeeId = :employeeId ORDER BY f.startYearMonth desc";
    private static final String SELECT_BY_EMP_ID_STRING = SELECT_ALL_QUERY_STRING + " WHERE f.empSalPriHisPk.personalUnitPriceCode = :personalUnitPriceCode AND f.empSalPriHisPk.employeeId = :employeeId AND f.startYearMonth <= :baseYearMonth AND f.endYearMonth >= :baseYearMonth ORDER BY f.empSalPriHisPk.personalUnitPriceCode";
    private static final String UPDATE_AMOUNT_BY_HISTORY_ID = "UPDATE QpbmtEmpSalPriHis f SET f.indvidualUnitPrice = :indvidualUnitPrice WHERE f.empSalPriHisPk.historyId = :historyId";
    private static final String UPDATE_HISTORY_BY_HISTORY_ID = "UPDATE QpbmtEmpSalPriHis f SET f.startYearMonth = :startYearMonth, f.endYearMonth = :endYearMonth WHERE f.empSalPriHisPk.historyId = :historyId";
    private static final String DELETE_HISTORY_BY_HISTORY_ID = "DELETE FROM QpbmtEmpSalPriHis f WHERE f.empSalPriHisPk.historyId = :historyId";
    private static final String UPDATE_OLD_HISTORY_BY_HISTORY_ID = "UPDATE QpbmtEmpSalPriHis f SET f.endYearMonth = :endYearMonth WHERE f.empSalPriHisPk.historyId = :historyId";

    @Override
    public void updateAllHistory(String historyId, BigDecimal UnitPrice) {
        this.getEntityManager().createQuery(UPDATE_AMOUNT_BY_HISTORY_ID,QpbmtEmpSalPriHis.class)
                .setParameter("historyId",historyId)
                .setParameter("indvidualUnitPrice",UnitPrice)
                .executeUpdate();
    }

    @Override
    public List<WorkIndividualPrice> getEmployeeSalaryUnitPriceHistory(String personalUnitPriceCode, List<String> employeeId, int yearMonthFilter) {
        if(employeeId==null || !(employeeId.size()>0))
            return Collections.emptyList();
        List<QpbmtEmpSalPriHis> qpbmtEmpSalPriHis = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpSalPriHis.class)
                .setParameter("personalUnitPriceCode", personalUnitPriceCode)
                .setParameter("employeeId", employeeId)
                .setParameter("yearMonthFilter", yearMonthFilter).getList();
        if(qpbmtEmpSalPriHis.size()>0){
            return qpbmtEmpSalPriHis.stream().map(v-> new WorkIndividualPrice(v.empSalPriHisPk.employeeId,v.empSalPriHisPk.historyId,"","",v.startYearMonth,v.endYearMonth,v.indvidualUnitPrice)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public EmployeeSalaryUnitPriceHistory getAllEmployeeSalaryUnitPriceHistory(String perUnitPriceCode, String employeeId) {
        List<QpbmtEmpSalPriHis> qpbmtEmpSalPriHis = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpSalPriHis.class)
                .setParameter("personalUnitPriceCode", perUnitPriceCode)
                .setParameter("employeeId", employeeId).getList();
        return QpbmtEmpSalPriHis.toDomain(qpbmtEmpSalPriHis);
    }

    @Override
    public void add(EmployeeSalaryUnitPriceHistory domain){
        this.commandProxy().insertAll(QpbmtEmpSalPriHis.toEntity(domain));
    }

    @Override
    public void update(EmployeeSalaryUnitPriceHistory domain){
        this.commandProxy().updateAll(QpbmtEmpSalPriHis.toEntity(domain));
    }

    @Override
    public void remove(String personalUnitPriceCode, String employeeId, String historyId) {
        this.commandProxy().remove(QpbmtEmpSalPriHis.class, new QpbmtEmpSalPriHisPk(personalUnitPriceCode, employeeId, historyId));
    }

    @Override
    public List<IndEmpSalUnitPriceHistory> getAllIndividualEmpSalUnitPriceHistory(String perUnitPriceCode, String employeeId) {
        return this.queryProxy().query(SELECT_BY_CODE_STRING, QpbmtEmpSalPriHis.class)
                .setParameter("personalUnitPriceCode", perUnitPriceCode)
                .setParameter("employeeId", employeeId)
                .getList().stream().map(QpbmtEmpSalPriHis::toDomain).collect(Collectors.toList());
    }

    @Override
    public void updateAmount(PayrollInformation domain) {
        this.getEntityManager().createQuery(UPDATE_AMOUNT_BY_HISTORY_ID,QpbmtEmpSalPriHis.class)
                .setParameter("historyId", domain.getHistoryID())
                .setParameter("indvidualUnitPrice", domain.getIndividualUnitPrice().v())
                .executeUpdate();
    }

    @Override
    public void addHistory(EmployeeSalaryUnitPriceHistory domain1, PayrollInformation domain2) {
        this.commandProxy().insert(QpbmtEmpSalPriHis.toEntity(domain1, domain2));
    }

    @Override
    public void updateHistory(EmployeeSalaryUnitPriceHistory domain) {
        this.getEntityManager().createQuery(UPDATE_HISTORY_BY_HISTORY_ID, QpbmtEmpSalPriHis.class)
                .setParameter("startYearMonth", domain.items().stream().map(item -> item.start().v()).findFirst().orElse(null))
                .setParameter("endYearMonth", domain.items().stream().map(item -> item.end().v()).findFirst().orElse(null))
                .setParameter("historyId", domain.items().stream().map(GeneralHistoryItem::identifier).findFirst().orElse(null))
                .executeUpdate();
    }

    @Override
    public void deleteHistory(String historyId) {
        this.getEntityManager().createQuery(DELETE_HISTORY_BY_HISTORY_ID, QpbmtEmpSalPriHis.class)
                .setParameter("historyId", historyId)
                .executeUpdate();
    }

    @Override
    public void updateOldHistory(String historyId, int newEndYearMonth) {
        this.getEntityManager().createQuery(UPDATE_OLD_HISTORY_BY_HISTORY_ID, QpbmtEmpSalPriHis.class)
                .setParameter("endYearMonth", newEndYearMonth)
                .setParameter("historyId", historyId)
                .executeUpdate();
    }

    @Override
    public  List<IndEmpSalUnitPriceHistory> getIndividualUnitPriceList(String perUnitPriceCode, String employeeId, int baseYearMonth) {
        return queryProxy().query(SELECT_BY_EMP_ID_STRING, QpbmtEmpSalPriHis.class)
                .setParameter("personalUnitPriceCode", perUnitPriceCode)
                .setParameter("baseYearMonth", baseYearMonth)
                .setParameter("employeeId", employeeId)
                .getList().stream().map(QpbmtEmpSalPriHis::toDomain).collect(Collectors.toList());
    }
}
