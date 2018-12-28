package nts.uk.ctx.pr.shared.infra.repository.salgenpurposeparam;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaIdentification;
import nts.uk.ctx.pr.shared.dom.salgenpurposeparam.SalGenParaIdentificationRepository;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QpbmtSalGenParamIdent;
import nts.uk.ctx.pr.shared.infra.entity.salgenpurposeparam.QpbmtSalGenParamIdentPk;

@Stateless
public class JpaSalGenParaIdentificationRepository extends JpaRepository implements SalGenParaIdentificationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenParamIdent f";
    private static final String SELECT_ALL_BY_CID = SELECT_ALL_QUERY_STRING+" WHERE f.salGenParamIdentPk.cid =:cid ORDER BY f.salGenParamIdentPk.paraNo ASC";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParamIdentPk.paraNo =:paraNo AND  f.salGenParamIdentPk.cid =:cid ";

    @Override
    public List<SalGenParaIdentification> getAllSalGenParaIdentification(String cid){
        return this.queryProxy().query(SELECT_ALL_BY_CID, QpbmtSalGenParamIdent.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalGenParaIdentification> getSalGenParaIdentificationById(String paraNo, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtSalGenParamIdent.class)
        .setParameter("paraNo", paraNo)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalGenParaIdentification domain){
        this.commandProxy().insert(QpbmtSalGenParamIdent.toEntity(domain));
    }

    @Override
    public void update(SalGenParaIdentification domain){
        this.commandProxy().update(QpbmtSalGenParamIdent.toEntity(domain));
    }

    @Override
    public void remove(String paraNo, String cid){
        this.commandProxy().remove(QpbmtSalGenParamIdent.class, new QpbmtSalGenParamIdentPk(paraNo, cid));
    }
}
