package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.paymentitemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset.QpbmtPaymentItemSt;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset.QpbmtPaymentItemStPk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPaymentItemStRepository extends JpaRepository implements PaymentItemSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPaymentItemSt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING +
            " WHERE  f.paymentItemStPk.cid =:cid" +
            " AND  f.paymentItemStPk.categoryAtr =:categoryAtr" +
            " AND  f.paymentItemStPk.itemNameCd =:itemNameCd ";
    private static final String UPDATE_IN_LIST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 1"
            + " WHERE f.paymentItemStPk.cid =:cid AND f.paymentItemStPk.itemNameCd IN :lstCode";
    private static final String UPDATE_NOT_IN_LIST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 0"
            + " WHERE f.paymentItemStPk.cid =:cid AND f.paymentItemStPk.itemNameCd NOT IN :lstCode";
    private static final String UPDATE_LIST_PAYMENTITEMST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 0 WHERE f.paymentItemStPk.cid =:cid";

    @Override
    public List<PaymentItemSet> getAllPaymentItemSt() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPaymentItemSt.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PaymentItemSet> getPaymentItemStById(String cid, int categoryAtr, String itemNameCd) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPaymentItemSt.class)
                .setParameter("cid", cid)
                .setParameter("categoryAtr", categoryAtr)
                .setParameter("itemNameCd", itemNameCd)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(PaymentItemSet domain) {
        this.commandProxy().insert(QpbmtPaymentItemSt.toEntity(domain));
    }

    @Override
    public void update(PaymentItemSet domain) {
        this.commandProxy().update(QpbmtPaymentItemSt.toEntity(domain));
    }

    @Override
    public void updateAll(List<String> lstCode) {
        String cid = AppContexts.user().companyId();

        if (lstCode.size() > 0) {
            this.getEntityManager().createQuery(UPDATE_IN_LIST).setParameter("cid", cid).setParameter("lstCode", lstCode).executeUpdate();
            this.getEntityManager().createQuery(UPDATE_NOT_IN_LIST).setParameter("cid", cid).setParameter("lstCode", lstCode).executeUpdate();
        }else{
            this.getEntityManager().createQuery(UPDATE_LIST_PAYMENTITEMST).executeUpdate();
        }
    }

    @Override
    public void remove(String cid, int categoryAtr, String itemNameCd) {
        this.commandProxy().remove(QpbmtPaymentItemSt.class, new QpbmtPaymentItemStPk(cid, categoryAtr, itemNameCd));
    }
}
