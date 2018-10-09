package nts.uk.ctx.pr.core.infra.repository.individualwagecontract;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmount;
import nts.uk.ctx.pr.core.infra.entity.individualwagecontract.QpbmtSalIndAmountPk;

@Stateless
public class JpaSalIndAmountRepository extends JpaRepository implements SalIndAmountRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmount f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountPk.historyId =:historyId ";

    @Override
    public List<SalIndAmount> getAllSalIndAmount(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalIndAmount.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalIndAmount> getSalIndAmountById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmount.class)
        .setParameter("historyId", historyId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalIndAmount domain){
        this.commandProxy().insert(QpbmtSalIndAmount.toEntity(domain));
    }

    @Override
    public void update(SalIndAmount domain){
        this.commandProxy().update(QpbmtSalIndAmount.toEntity(domain));
    }

    @Override
    public void remove(String historyId){
        this.commandProxy().remove(QpbmtSalIndAmount.class, new QpbmtSalIndAmountPk(historyId));
    }
}
