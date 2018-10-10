package nts.uk.ctx.pr.shared.infra.repository.payrollgeneralpurposeparameters;

import javax.ejb.Stateless;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaIdentification;
import nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters.SalGenParaIdentificationRepository;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParamIdent;
import nts.uk.ctx.pr.shared.infra.entity.payrollgeneralpurposeparameters.QpbmtSalGenParamIdentPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaSalGenParaIdentificationRepository extends JpaRepository implements SalGenParaIdentificationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtSalGenParamIdent f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.salGenParamIdentPk.paraNo =:paraNo AND  f.salGenParamIdentPk.cid =:cid ";

    @Override
    public List<SalGenParaIdentification> getAllSalGenParaIdentification(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtSalGenParamIdent.class)
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
