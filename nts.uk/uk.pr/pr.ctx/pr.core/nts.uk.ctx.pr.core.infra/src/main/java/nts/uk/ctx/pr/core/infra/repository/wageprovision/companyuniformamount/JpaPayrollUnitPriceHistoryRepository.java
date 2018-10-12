package nts.uk.ctx.pr.core.infra.repository.wageprovision.companyuniformamount;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.CompanyUnitPriceCode;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHis;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHisPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Stateless
public class JpaPayrollUnitPriceHistoryRepository extends JpaRepository implements PayrollUnitPriceHistoryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPriceHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriceHisPk.cid =:cid AND  f.payUnitPriceHisPk.code =:code AND  f.payUnitPriceHisPk.hisId =:hisId ";
    private static final String SELECT_BY_CID_CODE_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriceHisPk.cid =:cid AND  f.payUnitPriceHisPk.code =:code ORDER BY f.endYearMonth DESC";

    @Override
    public Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryById(String cid, String code, String hisId){
         List<QpbmtPayUnitPriceHis> temp = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPriceHis.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .setParameter("hisId", hisId)
               .getList();
        return Optional.of(new PayrollUnitPriceHistory(new CompanyUnitPriceCode(code),cid,toDomain(temp)));

    }

    @Override
    public Object[] getPayrollUnitPriceHistory(String cid, String code, String hisId){
        Optional<QpbmtPayUnitPriceHis> temp = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPriceHis.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .setParameter("hisId", hisId)
                .getSingle();
        YearMonthHistoryItem yearMonthHistoryItem = new YearMonthHistoryItem(temp.get().payUnitPriceHisPk.hisId, new YearMonthPeriod(new YearMonth(temp.get().startYearMonth), new YearMonth(temp.get().endYearMonth)));
        PayrollUnitPriceSetting payrollUnitPriceSetting = new PayrollUnitPriceSetting(temp.get().payUnitPriceHisPk.hisId, temp.get().amountOfMoney, temp.get().targetClass, temp.get().monthSalaryPerDay, temp.get().aDayPayee, temp.get().hourlyPay, temp.get().monthSalary, temp.get().setClassification, temp.get().notes);
        return new Object[]{ yearMonthHistoryItem, payrollUnitPriceSetting };
    }

    @Override
    public Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryByCidCode(String cid, String code) {
        List<QpbmtPayUnitPriceHis> temp = this.queryProxy().query(SELECT_BY_CID_CODE_STRING, QpbmtPayUnitPriceHis.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .getList();
        return Optional.of(new PayrollUnitPriceHistory(new CompanyUnitPriceCode(code),cid,toDomain(temp)));
    }

    private List<YearMonthHistoryItem> toDomain(List<QpbmtPayUnitPriceHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }

        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new YearMonthHistoryItem(entity.payUnitPriceHisPk.hisId,
                    new YearMonthPeriod(new YearMonth(entity.startYearMonth),
                            new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }


    @Override
    public void add(String code,String cId, YearMonthHistoryItem domain, PayrollUnitPriceSetting payrollUnitPriceSet) {
        this.commandProxy().insert(QpbmtPayUnitPriceHis.toEntity(code, cId, domain, payrollUnitPriceSet));
    }

    @Override
    public void update(String code,String cId, YearMonthHistoryItem domain, PayrollUnitPriceSetting payrollUnitPriceSet) {
        this.commandProxy().update(QpbmtPayUnitPriceHis.toEntity(code, cId, domain, payrollUnitPriceSet));
    }

    @Override
    public void update(String code,String cId, YearMonthHistoryItem domain) {
        Optional<QpbmtPayUnitPriceHis> payUnitPriceHis = this.queryProxy().find(new QpbmtPayUnitPriceHisPk(cId,code, domain.identifier()), QpbmtPayUnitPriceHis.class);
        if (payUnitPriceHis.isPresent()){
            payUnitPriceHis.get().startYearMonth = domain.start().v();
            payUnitPriceHis.get().endYearMonth = domain.end().v();
            this.commandProxy().update(payUnitPriceHis.get());
        }

    }

    @Override
    public void remove(String cid, String code, String hisId){
        this.commandProxy().remove(QpbmtPayUnitPriceHis.class, new QpbmtPayUnitPriceHisPk(cid, code, hisId));
    }
}
