package nts.uk.ctx.pr.core.infra.repository.wageprovision.companyuniformamount;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPrice;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPricePk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPayrollUnitPriceRepository extends JpaRepository implements PayrollUnitPriceRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPrice f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPricePk.code =:code AND  f.payUnitPricePk.cid =:cid ";
    private static final String SELECT_BY_KEY_STRING_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPricePk.cid =:cid ORDER BY f.payUnitPricePk.code ASC";
    private static final String SELECT_PAYROLL_UNIT_PRICE_BY_YEAR_MONTH = "SELECT a FROM QpbmtPayUnitPrice a JOIN QpbmtPayUnitPriceHis b ON a.payUnitPricePk.cid = b.payUnitPriceHisPk.cid AND a.payUnitPricePk.code = b.payUnitPriceHisPk.code WHERE a.payUnitPricePk.cid =:cid AND b.startYearMonth <=:yearMonth AND b.endYearMonth >=:yearMonth ORDER BY a.payUnitPricePk.code ";

    private static final String EXPORT_EXCEL = "SELECT  " +
            " t1.UNIT_PRICE_CD, " +
            " t1.UNIT_PRICE_NAME, " +
            " t2.START_YM, " +
            " t2.AMOUNT_OF_MONEY, " +
            " t2.TARGET_CLASS_ATR, " +
            "  t2.MONTH_SALARY_ATR, " +
            " t2.MONTH_SALARY_PER_DAY_ATR, " +
            " t2.A_DAY_PAYEE_ATR, " +
            " t2.HOURLY_PAY_ATR, " +
            " t2.SET_CLASSIFICATION_ATR, " +
            " ROW_NUMBER () OVER ( PARTITION BY t1.UNIT_PRICE_CD ORDER BY t1.UNIT_PRICE_CD ASC, t2.START_YM DESC ) AS ROW_NUMBER  " +
            "FROM " +
            " QPBMT_PAY_UNIT_PRICE t1 " +
            " INNER JOIN QPBMT_PAY_UNIT_PRICE_HIS t2 ON t1.CID = t2.CID  " +
            " AND t1.UNIT_PRICE_CD = t2.UNIT_PRICE_CD  " +
            "WHERE " +
            " t1.CID = ?cid" +
            " AND t2.CID = ?cid ";




    @Override
    public List<Object[]> getAllPayrollUnitPriceSetByCID(String cid) {
        return this.getEntityManager().createNativeQuery(EXPORT_EXCEL).setParameter("cid",cid).getResultList();
    }

    @Override
    public List<PayrollUnitPrice> getAllPayrollUnitPriceByCID(String cid) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING_BY_CID, QpbmtPayUnitPrice.class)
                .setParameter("cid", cid)
                .getList(item ->item.toDomain());
    }

    @Override
    public List<PayrollUnitPrice> getPayrollUnitPriceByYearMonth(int yearMonth) {
        return this.queryProxy().query(SELECT_PAYROLL_UNIT_PRICE_BY_YEAR_MONTH, QpbmtPayUnitPrice.class)
                .setParameter("cid", AppContexts.user().companyId())
                .setParameter("yearMonth", yearMonth)
                .getList(item ->item.toDomain());
    }

    @Override
    public Optional<PayrollUnitPrice> getPayrollUnitPriceById(String code, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPrice.class)
        .setParameter("code", code)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(PayrollUnitPrice domain){
        this.commandProxy().insert(QpbmtPayUnitPrice.toEntity(domain));
    }

    @Override
    public void update(PayrollUnitPrice domain){
        this.commandProxy().update(QpbmtPayUnitPrice.toEntity(domain));
    }

    @Override
    public void remove(String code, String cid){
        this.commandProxy().remove(QpbmtPayUnitPrice.class, new QpbmtPayUnitPricePk(cid, code));
    }
}
