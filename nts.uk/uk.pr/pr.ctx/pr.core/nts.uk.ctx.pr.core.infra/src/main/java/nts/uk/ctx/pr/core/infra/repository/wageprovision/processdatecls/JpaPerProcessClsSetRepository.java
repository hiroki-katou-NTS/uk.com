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
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.perProcesClsSetPk.processCateNo =:processCateNo ";

    @Override
    public List<PerProcessClsSet> getAllPerProcessClsSet(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPerProcesClsSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PerProcessClsSet> getPerProcessClsSetById(String processCateNo){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPerProcesClsSet.class)
        .setParameter("processCateNo", processCateNo)
        .getSingle(c->c.toDomain());
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
    public void remove(String processCateNo){
        this.commandProxy().remove(QpbmtPerProcesClsSet.class, new QpbmtPerProcesClsSetPk(processCateNo));
    }
}
