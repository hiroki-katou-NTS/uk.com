package nts.uk.ctx.pr.core.infra.repository.payrollgeneralpurposeparameters;

import java.util.Optional;
import java.util.List;
import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenPrOptions;
import nts.uk.ctx.pr.core.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenPrOptionsPk;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParamOptions;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParamOptionsRepository;

@Stateless
public class JpaSalGenParamOptionsRepository extends JpaRepository implements SalGenParamOptionsRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenPrOptions f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrOptionsPk.paraNo =:paraNo AND  f.salGenPrOptionsPk.cid =:cid AND  f.salGenPrOptionsPk.optionNo =:optionNo ";

    @Override
    public List<SalGenParamOptions> getAllSalGenParamOptions(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalGenPrOptions.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParamOptions> getSalGenParamOptionsById(String paraNo, String cid, int optionNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalGenPrOptions.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("optionNo", optionNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParamOptions domain){
        this.commandProxy().insert(QpbmtSalGenPrOptions.toEntity(domain));
    }

    @Override
    public void update(SalGenParamOptions domain){
        this.commandProxy().update(QpbmtSalGenPrOptions.toEntity(domain));
    }

    @Override
    public void remove(String paraNo, String cid, int optionNo){
        this.commandProxy().remove(QpbmtSalGenPrOptions.class, new QpbmtSalGenPrOptionsPk(paraNo, cid, optionNo));
    }
}
