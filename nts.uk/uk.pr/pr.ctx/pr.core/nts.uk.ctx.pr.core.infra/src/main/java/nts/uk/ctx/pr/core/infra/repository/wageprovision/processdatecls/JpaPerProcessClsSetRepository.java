package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSet;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSetRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtPerProcesClsSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtPerProcesClsSetPk;

@Stateless
public class JpaPerProcessClsSetRepository extends JpaRepository implements PerProcessClsSetRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPerProcesClsSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.perProcesClsSetPk.userId =:uid AND f.perProcesClsSetPk.companyId=:cid";

    @Override
    public List<PerProcessClsSet> getAllPerProcessClsSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPerProcesClsSet.class)
                .getList(QpbmtPerProcesClsSet::toDomain);
    }

    @Override
    public Optional<PerProcessClsSet> getPerProcessClsSetByUIDAndCID(String uid,String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPerProcesClsSet.class)
                .setParameter("uid", uid)
                .setParameter("cid",cid)
                .getSingle(QpbmtPerProcesClsSet::toDomain);
    }

    @Override
    public void add(PerProcessClsSet domain){
        this.commandProxy().insert(QpbmtPerProcesClsSet.toEntity(domain));
    }

    @Override
    public void update(PerProcessClsSet domain){
        this.commandProxy().update(QpbmtPerProcesClsSet.toEntity(domain));
    }

    @Override
    public void remove(String companyId, String userId){
        this.commandProxy().remove(QpbmtPerProcesClsSet.class, new QpbmtPerProcesClsSetPk(companyId, userId));
    }
}
