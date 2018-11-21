package nts.uk.ctx.pr.core.infra.repository.wageprovision.speclayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.PaymentItemDetailSet;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.PaymentItemDetailSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.QpbmtPayItemDetailSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.speclayout.QpbmtPayItemDetailSetPk;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPaymentItemDetailSetRepository extends JpaRepository implements PaymentItemDetailSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayItemDetailSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payItemDetailSetPk.histId =:histId ";

    @Override
    public List<PaymentItemDetailSet> getAllPaymentItemDetailSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPayItemDetailSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PaymentItemDetailSet> getPaymentItemDetailSetById(String histId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayItemDetailSet.class)
        .setParameter("histId", histId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(PaymentItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode){
        this.commandProxy().insert(QpbmtPayItemDetailSet.toEntity(domain, ymPeriod, cid, specCode));
    }

    @Override
    public void update(PaymentItemDetailSet domain, YearMonthPeriod ymPeriod, String cid, String specCode){
        this.commandProxy().update(QpbmtPayItemDetailSet.toEntity(domain, ymPeriod, cid, specCode));
    }

    @Override
    public void remove(String histId, String cid, String specCode){
        this.commandProxy().remove(QpbmtPayItemDetailSet.class, new QpbmtPayItemDetailSetPk(histId, cid, specCode));
    }
}
