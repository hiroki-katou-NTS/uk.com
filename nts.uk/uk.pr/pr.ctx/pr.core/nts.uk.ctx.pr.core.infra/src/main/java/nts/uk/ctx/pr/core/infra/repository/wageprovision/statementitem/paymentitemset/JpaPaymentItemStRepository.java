package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.paymentitemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset.QpbmtPaymentItemSt;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.paymentitemset.QpbmtPaymentItemStPk;

@Stateless
public class JpaPaymentItemStRepository extends JpaRepository implements PaymentItemSetRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPaymentItemSt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
            + " WHERE  f.paymentItemStPk.cid =:cid AND  f.paymentItemStPk.salaryItemId =:salaryItemId ";
    private static final String UPDATE_IN_LIST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 1"
            + " WHERE f.paymentItemStPk.salaryItemId IN :lstSalaryId";
    private static final String UPDATE_NOT_IN_LIST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 0"
            + " WHERE f.paymentItemStPk.salaryItemId NOT IN :lstSalaryId";
    private static final String UPDATE_LIST_PAYMENTITEMST = " UPDATE QpbmtPaymentItemSt f SET f.averageWageAtr = 0";

    @Override
    public List<PaymentItemSet> getAllPaymentItemSt() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPaymentItemSt.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PaymentItemSet> getPaymentItemStById(String cid, String salaryItemId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPaymentItemSt.class).setParameter("cid", cid)
                .setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
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
    public void updateAll(List<String> lstSalaryId) {
        if (lstSalaryId.size() > 0) {
            this.getEntityManager().createQuery(UPDATE_IN_LIST).setParameter("lstSalaryId", lstSalaryId).executeUpdate();
            this.getEntityManager().createQuery(UPDATE_NOT_IN_LIST).setParameter("lstSalaryId", lstSalaryId).executeUpdate();
        }else{
            this.getEntityManager().createQuery(UPDATE_LIST_PAYMENTITEMST).executeUpdate();
        }


    }

    @Override
    public void remove(String cid, String salaryItemId) {
        if (this.getPaymentItemStById(cid, salaryItemId).isPresent()) {
            this.commandProxy().remove(QpbmtPaymentItemSt.class, new QpbmtPaymentItemStPk(cid, salaryItemId));
        }
    }
}
