package nts.uk.ctx.pr.core.infra.repository.wageprovision.salaryindividualamountname;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountName;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname.QpbmtSalIndAmountName;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.salaryindividualamountname.QpbmtSalIndAmountNamePk;

@Stateless
public class JpaSalIndAmountNameRepository extends JpaRepository implements SalIndAmountNameRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalIndAmountName f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountNamePk.cid =:cid AND  f.salIndAmountNamePk.individualPriceCode =:individualPriceCode ";
    private static final String SELECT_BY_INDIVIDUAL = SELECT_ALL_QUERY_STRING + " WHERE  f.salIndAmountNamePk.cid =:cid AND f.salIndAmountNamePk.cateIndicator =:cateIndicator ";

    @Override
    public List<SalIndAmountName> getAllSalIndAmountName(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalIndAmountName.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalIndAmountName> getSalIndAmountNameById(String cid, String individualPriceCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalIndAmountName.class)
        .setParameter("cid", cid)
        .setParameter("individualPriceCode", individualPriceCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalIndAmountName domain){
        this.commandProxy().insert(QpbmtSalIndAmountName.toEntity(domain));
    }

    @Override
    public void update(SalIndAmountName domain){
        this.commandProxy().update(QpbmtSalIndAmountName.toEntity(domain));
    }

    @Override
    public void remove(String cid, String individualPriceCode,int cateIndicator){
        this.commandProxy().remove(QpbmtSalIndAmountName.class, new QpbmtSalIndAmountNamePk(cid, individualPriceCode,cateIndicator));
    }














    @Override
    public List<SalIndAmountName> getAllSalIndAmountName(String cid,int cateIndicator) {
        return this.queryProxy().query(SELECT_BY_INDIVIDUAL, QpbmtSalIndAmountName.class)
                .setParameter("cid",cid)
                .setParameter("cateIndicator",cateIndicator)
                .getList(item -> item.toDomain());
    }
}
