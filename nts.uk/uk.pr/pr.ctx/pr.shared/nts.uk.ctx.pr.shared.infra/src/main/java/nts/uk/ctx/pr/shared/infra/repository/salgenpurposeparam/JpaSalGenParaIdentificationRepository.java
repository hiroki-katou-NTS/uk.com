package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaIdentification;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaIdentificationRepository;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParamIdent;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QqsmtSalGenParamIdentPk;

@Stateless
public class JpaSalGenParaIdentificationRepository extends JpaRepository implements SalGenParaIdentificationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSalGenParamIdent f";
    private static final String SELECT_ALL_BY_CID = SELECT_ALL_QUERY_STRING+" WHERE f.salGenParamIdentPk.cid =:cid ORDER BY f.salGenParamIdentPk.paraNo ASC";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParamIdentPk.paraNo =:paraNo AND  f.salGenParamIdentPk.cid =:cid ";

    @Override
    public List<SalGenParaIdentification> getAllSalGenParaIdentification(String cid){
        return this.queryProxy().query(SELECT_ALL_BY_CID, QqsmtSalGenParamIdent.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParaIdentification> getSalGenParaIdentificationById(String paraNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtSalGenParamIdent.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParaIdentification domain){
        this.commandProxy().insert(QqsmtSalGenParamIdent.toEntity(domain));
    }

    @Override
    public void update(SalGenParaIdentification domain){
        this.commandProxy().update(QqsmtSalGenParamIdent.toEntity(domain));
    }

    @Override
    public void remove(String paraNo, String cid){
        this.commandProxy().remove(QqsmtSalGenParamIdent.class, new QqsmtSalGenParamIdentPk(paraNo, cid));
    }
}
