package repository.wageprovision.companyuniformamount;

import entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHis;
import entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHisPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.CompanyUnitPriceCode;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
@Stateless
public class JpaPayrollUnitPriceHistoryRepository extends JpaRepository implements PayrollUnitPriceHistoryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPriceHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriceHisPk.cid =:cid AND  f.payUnitPriceHisPk.code =:code AND  f.payUnitPriceHisPk.hisId =:hisId ";
    private static final String SELECT_BY_CID_CODE_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriceHisPk.cid =:cid AND  f.payUnitPriceHisPk.code =:code ";

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
    public Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryByCidCode(String cid, String code) {
        List<QpbmtPayUnitPriceHis> temp = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPriceHis.class)
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
    public void add(YearMonthHistoryItem domain, String cId, String code) {

    }



    @Override
    public void update(YearMonthHistoryItem domain, String cId, String code) {

    }

    @Override
    public void remove(String cid, String code, String hisId){
        this.commandProxy().remove(QpbmtPayUnitPriceHis.class, new QpbmtPayUnitPriceHisPk(cid, code, hisId));
    }
}
