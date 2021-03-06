package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementlayout;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentItemDetailSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.PaymentItemDetailSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtPayItemDetailSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout.QpbmtPayItemDetailSetPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPaymentItemDetailSetRepository extends JpaRepository implements PaymentItemDetailSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayItemDetailSet f";

    @Override
    public List<PaymentItemDetailSet> getAllPaymentItemDetailSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPayItemDetailSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public void add(PaymentItemDetailSet domain){
        //this.commandProxy().insert(QpbmtPayItemDetailSet.toEntity(domain));
    }

    @Override
    public void update(PaymentItemDetailSet domain){
        //this.commandProxy().update(QpbmtPayItemDetailSet.toEntity(domain));
    }

    @Override
    public void remove(String histId){
        //this.commandProxy().remove(QpbmtPayItemDetailSet.class, new QpbmtPayItemDetailSetPk(histId));
    }
}
