package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParamOptions;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParamOptionsRepository;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrOptions;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenPrOptionsPk;

@Stateless
public class JpaSalGenParamOptionsRepository extends JpaRepository implements SalGenParamOptionsRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenPrOptions f";
    private static final String SELECT_BY_KEY_PARANO = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrOptionsPk.paraNo =:paraNo AND  f.salGenPrOptionsPk.cid =:cid ORDER BY f.salGenPrOptionsPk.optionNo ASC";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenPrOptionsPk.paraNo =:paraNo AND  f.salGenPrOptionsPk.cid =:cid AND  f.salGenPrOptionsPk.optionNo =:optionNo ";

    @Override
    public List<SalGenParamOptions> getAllSalGenParamOptions(String paraNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_PARANO, QqsmtSalGenPrOptions.class)
                .setParameter("paraNo", paraNo)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParamOptions> getSalGenParamOptionsById(String paraNo, String cid, int optionNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSalGenPrOptions.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .setParameter("optionNo", optionNo)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParamOptions domain){
        this.commandProxy().insert(QqsmtSalGenPrOptions.toEntity(domain));
    }

    @Override
    public void update(SalGenParamOptions domain){
        this.commandProxy().update(QqsmtSalGenPrOptions.toEntity(domain));
    }

    @Override
    public void remove(String paraNo, String cid, int optionNo){
        this.commandProxy().remove(QqsmtSalGenPrOptions.class, new QqsmtSalGenPrOptionsPk(paraNo, cid, optionNo));
    }
}
